package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String loadTours(@RequestParam(value = "loggedInUser", defaultValue = "-1") String loggedInUser, Model model) {
        if (!loggedInUser.equals("-1")) {
            User user = userRepository.findByUsername(loggedInUser);
            model.addAttribute("tours", tourRepository.findAllByDriver(user.getUserid()));
        }
        return "driverTours";
    }


    @RequestMapping("/driverTourDeliveries")
    public String showDeliveriesOfTour(@RequestParam(value="tourId", defaultValue = "-1") long tourId, Model model) {
        List<Delivery> tourDeliveriesTemp = new ArrayList<>();
        List<TourDelivery> tourDeliveries = tourDeliveryRepository.findByTourIdOrderByOrderId(tourId);
        System.out.println("tourId: " + tourId);
        System.out.println("tourSize: " + tourDeliveries.size());
        for(int i = 0; i < tourDeliveries.size(); i++){
            tourDeliveriesTemp.add(deliveryRepository.findByDeliveryId(tourDeliveries.get(i).getDeliveryId()));
            System.out.println("delivery: " + i);
        }

        model.addAttribute("deliveries", tourDeliveriesTemp);
        
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


    /*@Transactional
    @PostMapping("/user/remove")
    public String removeUser(@RequestParam("usrId") long usrId){
        userRepository.removeByUserid(usrId);
        userRoleRepository.removeAllByUserid(usrId);
        return "redirect:/user";
    }

    @Modifying
    @PostMapping("/editUser")
    public ModelAndView saveUser(@Param("user") User user, @RequestParam(value = "Checkboxes", required = false, defaultValue = "-1") List<String> checked,
                                 @Param("newPassword") String newPassword, @Param("confirm") String confirm) {

        if(newPassword.equals(confirm) && !newPassword.equals("")) {
            user.setPassword(confirm);
        }

        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException ex) {
            System.out.println("Exception: " + ex.toString());
            return new ModelAndView("redirect:editUser?usrId=" + user.getUserid() + "&error");
        }

        if(checked.get(0).equals("-1"))
            userRoleRepository.removeAllByUserid(user.getUserid());
        else
            updateRoles(checked, user.getUserid());


        return new ModelAndView("redirect:/user");
    }*/

}