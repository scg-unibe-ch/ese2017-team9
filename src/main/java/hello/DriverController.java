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
    public String loadTours(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        model.addAttribute("tours", tourRepository.findAllByDriver(currentUser.getUserid()));

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

    @RequestMapping("/driverDelivery")
    public String driverDelivery(@RequestParam(value="deliveryId", defaultValue = "-1", required = false) long deliveryId,
                           Model model){
        if(deliveryId != -1){
            Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);
            model.addAttribute("currentDelivery", delivery);
            model.addAttribute("customer", customerRepository.findByCustomerId(delivery.getCustomer()));
        }
        model.addAttribute("deliveries", deliveryRepository.findByDeliveryId(deliveryId));

        return "/driverDelivery";


    }

    @Modifying
    @PostMapping("/driverTourDeliveries")
    public ModelAndView saveTour(@Param("tourId") long tourId, @RequestParam(value = "orderIds", required = false, defaultValue = "-1") List<Long> deliveryOrder) {
        long order = 0;
        for (long tourDeliveryId : deliveryOrder){
            order++;
            tourDeliveryRepository.setOrderIdbyDeliveryIdAndTourId(order, tourDeliveryId, tourId);
        }
        return new ModelAndView("redirect:/driverTours");
    }

}