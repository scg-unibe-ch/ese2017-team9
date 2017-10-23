package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeliveryController {
    @Autowired
    DeliveryRepository deliveryRepository;

    @RequestMapping("/delivery")
    public String delivery(@RequestParam(value="deliveryId", defaultValue = "-1", required = false) long deliveryId,
                       Model model){
        if(deliveryId != -1){model.addAttribute("currentDelivery", deliveryRepository.findByDeliveryId(deliveryId));}
        model.addAttribute("deliveries", deliveryRepository.findAll());
        return "delivery";
    }

    @RequestMapping("/editDelivery")
    public String editDelivery(@RequestParam(value="deliveryId", defaultValue = "-1") long deliveryId, Model model){
        if(deliveryId != -1){
            Delivery currentDelivery = deliveryRepository.findByDeliveryId(deliveryId);
            model.addAttribute("currentDelivery", currentDelivery);
        }
        else{
            Delivery currentDelivery = new Delivery();
            model.addAttribute("currentDelivery", currentDelivery);
        }
        return "editDelivery";
    }

    @Transactional
    @PostMapping("/delivery/remove")
    public String removeDelivery(@RequestParam("deliveryId") long deliveryId){
        deliveryRepository.removeByDeliveryId(deliveryId);
        return "redirect:/delivery";
    }

    @Modifying
    @PostMapping("/editDelivery")
    public String saveDelivery(@Param("delivery") Delivery delivery) {

        deliveryRepository.save(delivery);

        return "redirect:/delivery";
    }

}
