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
    public String processPreferencesForm(HttpSession session, @ModelAttribute @Valid PreferencesFormDTO preferencesFormDTO, Errors errors) {
        User existingUser = userRepository.findByUsername(preferencesFormDTO.getUsername());
        User currentUser = authenticationController.getUserFromSession(session);
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "main/preferences";
        }
        currentUser.setUsername(preferencesFormDTO.getUsername());
        userRepository.save(currentUser);
        session.setAttribute("user", currentUser.getId());
        return "redirect:/preferences";
    }

}
