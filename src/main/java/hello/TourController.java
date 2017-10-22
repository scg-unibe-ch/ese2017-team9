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

import java.util.List;

@Controller
public class TourController {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourDeliveryRepository tourDeliveryRepository;

    @RequestMapping("/tour")
    public String tour(@RequestParam(value="tourId", defaultValue = "-1", required = false) long tourId,
                       Model model){
        if(tourId != -1){model.addAttribute("currentTour", tourRepository.findByTourId(tourId));}
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

    /*public void updateTourDelivery(List<String> checked, long tourId){

        for(String checkedStr : checked){
            if(tourDeliveryRepository.findByTourId(tourId, checkedStr) == null) {
                TourDelivery tourDelivery = new TourDelivery(tourId, checkedStr);
                tourDeliveryRepository.save(tourDelivery);
            }
        }

    }*/

}
