package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("symptoms")
public class SymptomsController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private SymptomRepository symptomRepository;

    @GetMapping
    public String displaySymptoms(Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        model.addAttribute("symptomList", symptomRepository.findAllByUserId(currentUser.getId()));
        return "main/symptoms";
    }

}
