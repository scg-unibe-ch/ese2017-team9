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
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
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
    }

    */
}
