package Controller;

import PersistanceObject.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserController {

    @ModelAttribute("currentUser")
    public User getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return (currentUser == null) ? null: currentUser;
    }
}
