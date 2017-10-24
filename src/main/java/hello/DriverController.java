package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @RequestMapping("/driverTours")
    public String loadTours(@RequestParam(value = "loggedInUser", defaultValue = "-1") String loggedInUser, Model model) {
        if (!loggedInUser.equals("-1")) {
            User user = userRepository.findByUsername(loggedInUser);
            model.addAttribute("tours", tourRepository.findAllByDriver(user.getUserid()));
        }
        return "driverTours";
    }


    @RequestMapping("/driverTourDeliveries")
    public String delivery(@RequestParam(value="currentTour", defaultValue = "-1") long currentTour, Model model) {

        List tourDeliveries, tourDeliveriesTemp = null;
        tourDeliveries = tourDeliveryRepository.findByTourId(currentTour); /*.getDeliveryId()*/
        for(int i = 0; i < tourDeliveries.size(); i++){
            tourDeliveriesTemp.add(tourDeliveries.get(i));
        }
        
        model.addAttribute("deliveries", tourDeliveriesTemp);
        
        return "/driverTourDeliveries";


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
}