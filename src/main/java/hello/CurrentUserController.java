package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class CurrentUserController {

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("loggedInUser")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails loggedInUser) {
        User user = null;
        if(loggedInUser != null)
            user = userRepository.findByUsername(loggedInUser.getUsername());
        return (user == null) ? null: user;
    }

}
