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
    public String tour(@RequestParam(value="tourId", defaultValue = "-1", required = false) long tourId,
                       Model model){
        if(tourId != -1){
            model.addAttribute("currentTour", tourRepository.findByTourId(tourId));
            model.addAttribute("driver", userRepository.findByUserid(tourRepository.findByTourId(tourId).getDriver()));
            model.addAttribute("deliveryCount", tourDeliveryRepository.countByTourId(tourId));
        }
        model.addAttribute("tours", tourRepository.findAll());

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
    public ModelAndView saveTour(@Param("tour") Tour tour) {
        tour.setTourId(tourRepository.findByTourName(tour.getTourName()).getTourId());
        tourRepository.save(tour);

        //return new ModelAndView("redirect:/tour?tourId=" + tour.getTourId());
        return new ModelAndView("redirect:/tour");
    }

    @Modifying
    @RequestMapping("/addDelivery")
    public ModelAndView addDelivery(@RequestParam(value="tourId") long tourId, @RequestParam(value="deliveryId") long deliveryId, Model model){
        if(tourDeliveryRepository.findByTourIdAndDeliveryId(tourId, deliveryId) == null){
            tourDeliveryRepository.save(new TourDelivery(tourId, deliveryId));
            System.out.println(tourId);
        }
        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }

    @Transactional
    @RequestMapping("/removeDelivery")
    public ModelAndView removeDelivery(@RequestParam(value="tourId") long tourId, @RequestParam(value="deliveryId") long deliveryId, Model model){
        deliveryRepository.setStatusByDeliveryId(deliveryId, "Open");
        tourDeliveryRepository.removeByTourIdAndDeliveryId(tourId, deliveryId);
        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }

    private void addSelectedDeliveriesToModel (long tourId, Model model){
        List<TourDelivery> delSel = tourDeliveryRepository.findByTourId(tourId);
        List<Delivery> deliveriesSelected = new ArrayList<>();
        for(int i = 0; i< delSel.size(); i++){
            deliveriesSelected.add(deliveryRepository.findByDeliveryId(delSel.get(i).getDeliveryId()));
        }
        model.addAttribute("deliveriesSelected", deliveriesSelected);

    }

    @RequestMapping("/addDeliveryPopUp")
    public String addDeliveryPopUp(@RequestParam(value="tourId") long tourId, Model model){
        model.addAttribute("currentTour", tourRepository.findByTourId(tourId));

        model.addAttribute("deliveries", deliveryRepository.findAllDeliveryNotScheduled());
        addSelectedDeliveriesToModel(tourId, model);
        model.addAttribute("drivers", userRepository.findAllUserByRole("ROLE_USER"));
        return "addDeliveryPopUp";
    }

    @Modifying
    @PostMapping("/addDeliveryPopUp")
    public ModelAndView saveDeliveries(@Param("tourId") Long tourId, @RequestParam(value = "Checkboxes", required = false, defaultValue = "-1") List<String> deliveryIds){
        if(deliveryIds.get(0).equals("-1"))
            return new ModelAndView("redirect:/editTour?tourId=" + tourId);
        else{
            for (String deliveryId:deliveryIds) {
                long deliveryIdLong = Long.parseLong(deliveryId);
                deliveryRepository.setStatusByDeliveryId(deliveryIdLong, "Scheduled");
                tourDeliveryRepository.save(new TourDelivery(tourId, deliveryIdLong));
            }
        }

        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }





}
