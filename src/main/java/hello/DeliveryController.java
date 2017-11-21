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

@Controller
public class DeliveryController {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/delivery")
    public String delivery(@RequestParam(value="filter", defaultValue="noFilter", required=false) String filter , Model model){
        model.addAttribute("deliveries", deliveryRepository.findAll());

        switch(filter){
            case "noFilter":
                model.addAttribute("deliveries", deliveryRepository.findAll());
                model.addAttribute("filter", filter);
                break;
            case "open":
                model.addAttribute("deliveries", deliveryRepository.findAllDeliveryByStatus("Open"));
                model.addAttribute("filter", filter);
                break;
            case "scheduled":
                model.addAttribute("deliveries", deliveryRepository.findAllDeliveryByStatus("Scheduled"));
                model.addAttribute("filter", filter);
                break;
            case "deliverySuccess":
                model.addAttribute("deliveries", deliveryRepository.findAllDeliveryByStatus("Delivery Success"));
                model.addAttribute("filter", filter);
                break;
            case "deliveryFailed":
                model.addAttribute("deliveries", deliveryRepository.findAllDeliveryByStatus("Delivery Failed"));
                model.addAttribute("filter", filter);
                break;
            case "noDeliveryPossible":
                model.addAttribute("deliveries", deliveryRepository.findAllDeliveryByStatus("No Delivery Possible"));
                model.addAttribute("filter", filter);
                break;
            default:
                model.addAttribute("deliveries", deliveryRepository.findAll());

        }

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
        model.addAttribute("customers", customerRepository.findAll());
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

        System.out.println("get time: "+delivery.getDeliveryWindowEnd().getTime());
        System.out.println("get delend: "+delivery.getDeliveryWindowEnd());
        deliveryRepository.save(delivery);

        return "redirect:/delivery";
    }

}
