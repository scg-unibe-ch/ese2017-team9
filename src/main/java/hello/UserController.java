package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @RequestMapping("/user")
    public String user(@RequestParam(value="filter", defaultValue="No Filter", required=false) String filter , Model model){
        model.addAttribute("users", userRepository.findAll());

        switch(filter){
            case "noFilter":
                model.addAttribute("users", userRepository.findAll());
                model.addAttribute("filter", filter);
                break;
            case "lockedUsers":
                model.addAttribute("users", userRepository.findByLockedTrue());
                model.addAttribute("filter", filter);
                break;

            case "drivers":
                model.addAttribute("users", userRepository.findAllUserByRole("ROLE_USER"));
                model.addAttribute("filter", filter);
                break;

            case "admins":
                model.addAttribute("users", userRepository.findAllUserByRole("ROLE_ADMIN"));
                model.addAttribute("filter", filter);
                break;

            default:
                model.addAttribute("users", userRepository.findAll());
        }
        return "user";
    }


    @RequestMapping("/editUser")
    public String editUser(@RequestParam(value="usrId", defaultValue = "-1") long usrId, Model model){
        if(usrId != -1){
            User currentUser = userRepository.findByUserid(usrId);
            List<String> roles = userRoleRepository.findRoleByUserName(currentUser.getUsername());
            isRole(roles, currentUser, model);
            model.addAttribute("currentUser", currentUser);
        }
        else{
            User currentUser = new User();
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isUser", false);
        }
        return "editUser";
    }

    @Transactional
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


    public void isRole(List<String> roles, User user, Model model){

        model.addAttribute("isAdmin", user.isAdmin(roles));
        model.addAttribute("isUser", user.isUser(roles));
    }

    public void updateRoles(List<String> checked, long userId){

        for(String checkedStr : checked){
            if(userRoleRepository.findByUseridAndRole(userId, checkedStr) == null) {
                UserRole userRole = new UserRole(userId, checkedStr);
                userRoleRepository.save(userRole);
            }
        }

        if(checked.size() != 2){
            if(checked.get(0).equals("ROLE_ADMIN"))
                userRoleRepository.removeByUseridAndRole(userId, "ROLE_USER");
            else
                userRoleRepository.removeByUseridAndRole(userId, "ROLE_ADMIN");
        }

    }

}
