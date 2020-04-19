package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.models.Email.EmailService;
import org.launchcode.MigraineManager.models.dto.FeedbackFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/welcome")
@Controller
public class WelcomeController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String displayWelcomePage(Model model) {
        if (!model.containsAttribute("feedbackFormDTO")) {
            model.addAttribute(new FeedbackFormDTO());
        }
        return "welcome";
    }

    @PostMapping
    public String processFeedbackForm(@Valid @ModelAttribute("feedbackFormDTO") FeedbackFormDTO feedbackFormDTO, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("org.launchcode.MigraineManager.models.dto.FeedbackFormDTO", errors.getAllErrors());
            model.addAttribute("feedbackFormDTO", feedbackFormDTO);
            return "welcome";
        }
        emailService.sendMail("mymigrainemanager@gmail.com", "Feedback", "Feedback from: " + feedbackFormDTO.getName()
                + " " + feedbackFormDTO.getEmail() + "\n" + feedbackFormDTO.getMessage());
        model.addAttribute(new FeedbackFormDTO());
        model.addAttribute("success", true);
        return "welcome";
    }

}
