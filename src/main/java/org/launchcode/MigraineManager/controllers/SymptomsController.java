package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.models.Symptom;
import org.launchcode.MigraineManager.models.User;
import org.launchcode.MigraineManager.models.dto.LoginFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

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
        model.addAttribute(new Symptom());
        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);
        return "main/symptoms";
    }

    @PostMapping(params = "addSymptom")
    public String processAddSymptomForm(HttpSession session, @ModelAttribute @Valid Symptom symptom, Errors errors, Model model) {
        User currentUser = authenticationController.getUserFromSession(session);
        symptom.setUserId(currentUser.getId());

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);

        if (symptom.getName() == null || symptom.getName().isEmpty()) {
            errors.rejectValue("name", "field.invalid", "Please enter a valid symptom");
            return "main/symptoms";
        } else {
            String str = symptom.getName();
            String capitalized = str.substring(0, 1).toUpperCase() + str.substring(1);
            symptom.setName(capitalized);
        }

        symptomRepository.save(symptom);
        return "redirect:/symptoms";
    }


    @PostMapping(params = "deleteSymptom")
    public String processDeleteSymptomForm(HttpSession session, @RequestParam(value = "resultList", required = false) List<String> resultList) {
        try {
            User currentUser = authenticationController.getUserFromSession(session);
            List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());

            if (resultList == null || resultList.isEmpty()) {
                return "main/symptoms";
            }

            for (String result : resultList) {
                for (Symptom symptom : symptomList) {
                    if (result.equals(symptom.getName())) {
                        symptomRepository.delete(symptom);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "redirect:/symptoms";
    }

}

