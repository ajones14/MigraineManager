package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.models.dto.FeedbackFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/welcome")
@Controller
public class WelcomeController {

    @GetMapping
    public String displayWelcomePage(Model model) {
        model.addAttribute(new FeedbackFormDTO());
        return "welcome";
    }

    @PostMapping
    public String processMessageForm() {
        return "redirect:/welcome";
    }

}
