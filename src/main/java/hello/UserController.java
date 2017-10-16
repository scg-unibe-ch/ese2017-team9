package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import javax.validation.constraints.Null;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    /*@RequestMapping("/user")
    public String user(Model model){
        model.addAttribute("users", userRepository.findAll());

        return "user";
    }
*/
    @RequestMapping("/user")
    public String user(@RequestParam(value="usrId", defaultValue = "-1", required = false) long usrId, Model model){
        if(usrId != -1){model.addAttribute("usrId", userRepository.findByUserid(usrId));}
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }
}
