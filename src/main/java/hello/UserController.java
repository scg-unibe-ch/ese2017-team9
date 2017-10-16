package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user")
    public String user(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }
}
