package Controller;

import PersistanceObject.User;
import Repository.UserRepository;
import PersistanceObject.UserRole;
import Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @RequestMapping("/user")
    public String user(@RequestParam(value="usrId", defaultValue = "-1", required = false) long usrId,
                       Model model){
        if(usrId != -1){model.addAttribute("currentUser", userRepository.findByUserid(usrId));}
        model.addAttribute("users", userRepository.findAll());
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
            model.addAttribute("isLogistician", false);
            model.addAttribute("isDriver", false);

        }
        return "editUser";
    }

    @Transactional
    @PostMapping("/user/remove")
    public String removeUser(@RequestParam("usrId") long usrId){
        userRepository.removeByUserid(usrId);
        return "redirect:/user";
    }

    @Modifying
    @PostMapping("/editUser")
    public String saveUser(@Param("user") User user, @RequestParam("Checkboxes") List<String> checked,
                           @Param("newPassword") String newPassword, @Param("confirm") String confirm) {

        System.out.println("password user: " + user.getPassword());
        System.out.println("password password: " + newPassword);
        System.out.println("password confirm: " + confirm);

        if(newPassword.equals(confirm) && !newPassword.equals("")) {
            user.setPassword(confirm);
        }

        userRepository.save(user);
        updateRoles(checked, user.getUserId());

        return "redirect:/user";
    }


    public void isRole(List<String> roles, User user, Model model){
        if(user.isUserAdmin(roles))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        if(user.isLogistician(roles))
            model.addAttribute("isLogistician", true);
        else
            model.addAttribute("isLogistician", false);

        if(user.isDriver(roles))
            model.addAttribute("isDriver", true);
        else
            model.addAttribute("isDriver", false);
    }

    public void updateRoles(List<String> checked, long userid){

        userRoleRepository.removeAllByUserid(userid);
        if(checked != null){
            for(String checkedStr : checked){
                UserRole userRole = new UserRole(userid, checkedStr);
                userRoleRepository.save(userRole);
            }
        }


    }

}
