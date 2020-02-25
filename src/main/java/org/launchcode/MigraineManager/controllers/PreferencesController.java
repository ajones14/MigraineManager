package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.UserRepository;
import org.launchcode.MigraineManager.models.User;
import org.launchcode.MigraineManager.models.dto.PreferencesFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("preferences")
public class PreferencesController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String displayPreferencesForm(Model model) {
        model.addAttribute(new PreferencesFormDTO());
        return "main/preferences";
    }

    @PostMapping(params = "changeUsername")
    public String processChangeUsernameForm(HttpSession session, @ModelAttribute @Valid PreferencesFormDTO preferencesFormDTO, Errors errors) {
        String username = preferencesFormDTO.getUsername();
        User existingUser = userRepository.findByUsername(username);
        User currentUser = authenticationController.getUserFromSession(session);

        if (username.isEmpty()) {
            errors.rejectValue("username", "field.empty", "Please enter a username");
            return "main/preferences";
        }
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "main/preferences";
        }
        currentUser.setUsername(username);
        userRepository.save(currentUser);
        return "redirect:/preferences";
    }

    @PostMapping(params = "changePassword")
    public String processChangePasswordForm(HttpSession session, @ModelAttribute @Valid PreferencesFormDTO preferencesFormDTO, Errors errors) {
        User currentUser = authenticationController.getUserFromSession(session);
        String password = preferencesFormDTO.getPassword();
        String verifyPassword = preferencesFormDTO.getVerifyPassword();

        if (password.isEmpty()) {
            errors.rejectValue("password", "passwords.empty", "Please enter a password");
            return "main/preferences";
        }
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "main/preferences";
        }
        currentUser.setNewPassword(password);
        userRepository.save(currentUser);
        return "redirect:/preferences";
    }

    @PostMapping(params = "changeFirstName")
    public String processChangeFirstName(HttpSession session, @ModelAttribute @Valid PreferencesFormDTO preferencesFormDTO, Errors errors) {
        User currentUser = authenticationController.getUserFromSession(session);
        String firstName = preferencesFormDTO.getFirstName();

        if (firstName.isEmpty()) {
            errors.rejectValue("firstName", "field.empty", "Please enter a first name");
            return "main/preferences";
        }

        currentUser.setFirstName(firstName);
        userRepository.save(currentUser);
        return "redirect:/preferences";
    }

    @PostMapping(params = "changeLastName")
    public String processChangeLastName(HttpSession session, @ModelAttribute @Valid PreferencesFormDTO preferencesFormDTO, Errors errors) {
        User currentUser = authenticationController.getUserFromSession(session);
        String lastName = preferencesFormDTO.getLastName();

        if (lastName.isEmpty()) {
            errors.rejectValue("lastName", "field.empty", "Please enter a last name");
            return "main/preferences";
        }

        currentUser.setLastName(lastName);
        userRepository.save(currentUser);
        return "redirect:/preferences";
    }

}
