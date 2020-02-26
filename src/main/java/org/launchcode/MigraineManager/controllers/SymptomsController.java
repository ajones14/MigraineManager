package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.models.Symptom;
import org.launchcode.MigraineManager.models.User;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        model.addAttribute(new Symptom());
        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);
        return "main/symptoms";
    }

    @PostMapping(params = "addSymptom")
    public String processAddSymptomForm(HttpSession session, @ModelAttribute @Valid Symptom symptom, Errors errors) {
        User currentUser = authenticationController.getUserFromSession(session);
        symptom.setUserId(currentUser.getId());

        if (symptom.getName() == null) {
            errors.rejectValue("name", "field.invalid", "Please enter a valid symptom");
            return "main/symptoms";
        }

        String str = symptom.getName();
        String capitalized = str.substring(0, 1).toUpperCase() + str.substring(1);
        symptom.setName(capitalized);

        symptomRepository.save(symptom);
        return "redirect:/symptoms";
    }

}
