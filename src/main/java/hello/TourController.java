package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TourController {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourDeliveryRepository tourDeliveryRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/tour")
    public String tour(@RequestParam(value="filter", defaultValue="No Filter", required=false) String filter , Model model){


        if(filter.equals("No Filter")) {
            model.addAttribute("tours", tourRepository.findAll());
            model.addAttribute("filter", filter);
        }
        else {
            model.addAttribute("tours", tourRepository.findAllByDriver(Long.parseLong(filter)));
            model.addAttribute("filter", Long.parseLong(filter));
        }
        model.addAttribute("drivers", userRepository.findAllUserByRole("ROLE_USER"));


        return "tour";
    }

    @RequestMapping("/editTour")
    public String editTour(@RequestParam(value="tourId", defaultValue = "-1") long tourId, Model model){
        if(tourId != -1){
            Tour currentTour = tourRepository.findByTourId(tourId);
            model.addAttribute("currentTour", currentTour);
        }
        else{
            model.addAttribute("currentTour", new Tour());
        }

        model.addAttribute("deliveries", deliveryRepository.findAllDeliveryNotScheduled());
        addSelectedDeliveriesToModel(tourId, model);
        model.addAttribute("drivers", userRepository.findAllUserByRole("ROLE_USER"));
        return "editTour";
    }

    @Transactional
    @PostMapping("/tour/remove")
    public String removeTour(@RequestParam("tourId") long tourId){
        tourRepository.removeByTourId(tourId);
        tourDeliveryRepository.removeAllByTourId(tourId);
        return "redirect:/tour";
    }

    @Modifying
    @PostMapping("/editTour")
    public ModelAndView saveTour(@Param("tour") Tour tour, @RequestParam(value = "orderIds", required = false, defaultValue = "-1") List<Long> deliveryOrder) {
        tourRepository.save(tour);
        long order = 0;
        long tourId = tour.getTourId();
        for (long tourDeliveryId : deliveryOrder){
            order++;
            tourDeliveryRepository.setOrderIdbyDeliveryIdAndTourId(order, tourDeliveryId, tourId);
        }
        return new ModelAndView("redirect:/tour");
    }

    @Transactional
    @RequestMapping("/removeDelivery")
    public ModelAndView removeDelivery(@RequestParam(value="tourId") long tourId, @RequestParam(value="deliveryId") long deliveryId, Model model){
        deliveryRepository.setStatusByDeliveryId(deliveryId, "Open");
        tourDeliveryRepository.removeByTourIdAndDeliveryId(tourId, deliveryId);
        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }

    private void addSelectedDeliveriesToModel (long tourId, Model model){
        List<TourDelivery> delSel = tourDeliveryRepository.findByTourIdOrderByOrderId(tourId);
        List<Delivery> deliveriesSelected = new ArrayList<>();
        for(int i = 0; i< delSel.size(); i++){
            deliveriesSelected.add(deliveryRepository.findByDeliveryId(delSel.get(i).getDeliveryId()));
        }
        model.addAttribute("deliveriesSelected", deliveriesSelected);

    }

    @Modifying
    @PostMapping("/addDeliveryPopUp")
    public ModelAndView saveDeliveries(@Param("tourId") Long tourId, @RequestParam(value = "Checkboxes", required = false, defaultValue = "-1") List<String> deliveryIds){
         if(deliveryIds.get(0).equals("-1"))
            return new ModelAndView("redirect:/editTour?tourId=" + tourId);
        else{
            long lowestOrder;
             lowestOrder = tourDeliveryRepository.getLowestOrderIdByTourId(tourId);

             for (String deliveryId:deliveryIds) {
                 lowestOrder++;
                 long deliveryIdLong = Long.parseLong(deliveryId);
                 deliveryRepository.setStatusByDeliveryId(deliveryIdLong, "Scheduled");
                 tourDeliveryRepository.save(new TourDelivery(tourId, deliveryIdLong, lowestOrder));
            }
        }

        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }

    @RequestMapping("/showDeliveriesPopup")
    public String showDeliveriesFromTour(@Param("tourId") Long tourId){
        System.out.println("something");
        return "tour";
    }

}
