package hello;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserController {

    @ModelAttribute("loggedInUser")
    public User getCurrentUser(@AuthenticationPrincipal User loggedInUser) {
        return (loggedInUser == null) ? null: loggedInUser;
    }
}
