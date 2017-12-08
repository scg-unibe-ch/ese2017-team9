package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverController {
    @Autowired
    TourRepository tourRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TourDeliveryRepository tourDeliveryRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    CustomerRepository customerRepository;


    @RequestMapping("/driverTours")
    public String loadTours(@RequestParam(value = "all", defaultValue = "false") boolean all, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        List<Tour> tours = tourRepository.findAllByDriverOrderByDeliverDay(currentUser.getUserid());
        if(all)
            model.addAttribute("tours", tours);
        else{
            Date today = new Date();
            for(Tour tour : tours){
                if(tour.getDeliverDay().before(today))
                    tours.remove(tour);
            }
            model.addAttribute("tours", tours);
        }

        return "driverTours";
    }


    @RequestMapping("/driverTourDeliveries")
    public String showDeliveriesOfTour(@RequestParam(value="tourId", defaultValue = "-1") long tourId, Model model) {
        List<Delivery> tourDeliveriesTemp = new ArrayList<>();
        List<TourDelivery> tourDeliveries = tourDeliveryRepository.findByTourIdOrderByOrderId(tourId);
        for(int i = 0; i < tourDeliveries.size(); i++){
            tourDeliveriesTemp.add(deliveryRepository.findByDeliveryId(tourDeliveries.get(i).getDeliveryId()));
        }
        model.addAttribute("tour", tourRepository.findByTourId(tourId));

        model.addAttribute("deliveries", tourDeliveriesTemp);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        long driverId = tourRepository.findByTourId(tourId).getDriver();
        if(currentUser.getUserid() != driverId)
            return "/driverTours";

        return "/driverTourDeliveries";
    }


    @Modifying
    @PostMapping(value = "/driverTourDeliveries")
    public ModelAndView saveTour(@Param("tourId") long tourId, @RequestParam(value = "orderIds", required = false, defaultValue = "-1") List<Long> deliveryOrder) {
        if(deliveryOrder.get(0) != -1) {
            long order = 0;
            for (long tourDeliveryId : deliveryOrder) {
                order++;
                tourDeliveryRepository.setOrderIdbyDeliveryIdAndTourId(order, tourDeliveryId, tourId);
                System.out.println("order: " + order);
                System.out.println("id: " + tourDeliveryId);
            }
        }
        System.out.println("tourId: " + tourId);


        return new ModelAndView("redirect:/driverTours");
    }

    @RequestMapping("/driverDeliveryStatus")
    public String driverDeliveryStatus(@RequestParam("deliveryId") long deliveryId ,Model model){
        model.addAttribute("delivery", deliveryRepository.findByDeliveryId(deliveryId));
        model.addAttribute("tourId", tourDeliveryRepository.findTourIdByDeliveryId(deliveryId));
        return "driverDeliveryStatus";
    }

    @Modifying
    @PostMapping("/driverDeliveryStatus")
    public ModelAndView saveStatusAndComment(@Param("delivery") Delivery delivery) {
        long deliveryId = delivery.getDeliveryId();
        deliveryRepository.setStatusByDeliveryId(deliveryId, delivery.getStatus());
        deliveryRepository.setCommentByDeliveryId(deliveryId, delivery.getComment());
        long tourId = tourDeliveryRepository.findTourIdByDeliveryId(deliveryId);
        return new ModelAndView("redirect:/driverTourDeliveries?tourId=" + tourId);
    }



}