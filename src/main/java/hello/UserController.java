package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.constraints.Null;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user")
    public String user(@RequestParam(value="usrId", defaultValue = "-1", required = false) long usrId,
                       Model model){
        if(usrId != -1){model.addAttribute("currentUser", userRepository.findByUserid(usrId));}
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }

    @Transactional
    @PostMapping("/user/remove")
    public String removeUser(@RequestParam("usrId") long usrId){
        userRepository.removeByUserid(usrId);
        return "redirect:/user";
    }

}
