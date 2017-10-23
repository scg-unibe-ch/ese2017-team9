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

    @RequestMapping("/tour")
    public String tour(@RequestParam(value="tourId", defaultValue = "-1", required = false) long tourId,
                       Model model){
        if(tourId != -1){
            model.addAttribute("currentTour", tourRepository.findByTourId(tourId));
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

        model.addAttribute("deliveries", deliveryRepository.findAll());
        List<TourDelivery> delSel = tourDeliveryRepository.findByTourId(tourId);
        List<Delivery> deliveriesSelected = new ArrayList<>();
        for(int i = 0; i< delSel.size(); i++){
            deliveriesSelected.add(deliveryRepository.findByDeliveryId(delSel.get(i).getDeliveryId()));
        }
        model.addAttribute("deliveriesSelected", deliveriesSelected);
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
    public String saveTour(@Param("tour") Tour tour) {

        tourRepository.save(tour);

        return "redirect:/tour";
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
        tourDeliveryRepository.removeByTourIdAndDeliveryId(tourId, deliveryId);
        return new ModelAndView("redirect:/editTour?tourId=" + tourId);
    }

}
