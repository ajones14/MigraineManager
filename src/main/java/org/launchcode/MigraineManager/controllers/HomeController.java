package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.*;
import org.launchcode.MigraineManager.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MigraineRepository migraineRepository;

    @Autowired
    private TriggerRepository triggerRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu");

    @GetMapping
    public String displayHome(RedirectAttributes redirectAttributes) {
        LocalDate now = LocalDate.now();
        redirectAttributes.addAttribute("date", now.format(formatter));
        return "redirect:/home/{date}";
    }

    @GetMapping(value = "{date}")
    public String scrollDate(@PathVariable("date") String date, Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        model.addAttribute("user", currentUser);
        LocalDate newDate = LocalDate.parse(date, formatter);

        List<Migraine> migraineList = migraineRepository.findAllByUserId(currentUser.getId());
        if (migraineList == null || migraineList.isEmpty()) {
            model.addAttribute("migraine", new Migraine(currentUser.getId()));
        } else {
            for (Migraine migraine : migraineList) {
                if (migraine.getEndTime() == null) {
                    model.addAttribute("migraine", migraine);
                } else {
                    model.addAttribute("migraine", new Migraine(currentUser.getId()));
                }
            }
        }

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        triggerList.sort(Comparator.comparing(Trigger::getName));
        model.addAttribute("triggerList", triggerList);

        List<Trigger> savedTriggers = new ArrayList<>();
        for (Trigger trigger : triggerList) {
            if (trigger.getDatesOccurred().contains(newDate)) {
                savedTriggers.add(trigger);
            }
        }

        List<Symptom> savedSymptoms = new ArrayList<>();
        for (Symptom symptom : symptomList) {
            if (symptom.getDatesOccurred().contains(newDate)) {
                savedSymptoms.add(symptom);
            }
        }

        model.addAttribute("savedTriggers", savedTriggers);
        model.addAttribute("savedSymptoms", savedSymptoms);
        model.addAttribute("date", date);
        model.addAttribute("forwardDate", newDate.plusDays(1).format(formatter));
        model.addAttribute("backwardDate", newDate.minusDays(1).format(formatter));
        return "main/home";
    };

    @PostMapping(value = "{date}/saveTriggers")
    public String processSaveTriggerForm(HttpSession session, @PathVariable("date") String date, @RequestParam(value = "resultList", required = false) List<String> resultList) {
        User currentUser = authenticationController.getUserFromSession(session);
        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        LocalDate newDate = LocalDate.parse(date, formatter);

        if (resultList == null || resultList.isEmpty()) {
            for (Trigger trigger : triggerList) {
                if (trigger.getDatesOccurred().contains(newDate)) {
                    trigger.removeDateOccurred(newDate);
                    triggerRepository.save(trigger);
                }
            }
            return "redirect:/home/{date}";
        }
        for (Trigger trigger : triggerList) {
            if (resultList.contains(trigger.getName()) && !(trigger.getDatesOccurred().contains(newDate))) {
                trigger.addDateOccurred(newDate);
                triggerRepository.save(trigger);
            } else if (!resultList.contains(trigger.getName()) && trigger.getDatesOccurred().contains(newDate)) {
                trigger.removeDateOccurred(newDate);
                triggerRepository.save(trigger);
            }
        }

        return "redirect:/home/{date}";
    }

    @PostMapping(value = "{date}/saveSymptoms")
    public String processSaveSymptomForm(HttpSession session, @PathVariable("date") String date, @RequestParam(value = "resultList", required = false) List<String> resultList) {
        User currentUser = authenticationController.getUserFromSession(session);
        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        LocalDate newDate = LocalDate.parse(date, formatter);

        if (resultList == null || resultList.isEmpty()) {
            for (Symptom symptom : symptomList) {
                if (symptom.getDatesOccurred().contains(newDate)) {
                    symptom.removeDateOccurred(newDate);
                    symptomRepository.save(symptom);
                }
            }
            return "redirect:/home/{date}";
        }
        for (Symptom symptom : symptomList) {
            if (resultList.contains(symptom.getName()) && !(symptom.getDatesOccurred().contains(newDate))) {
                symptom.addDateOccurred(newDate);
                symptomRepository.save(symptom);
            } else if (!resultList.contains(symptom.getName()) && symptom.getDatesOccurred().contains(newDate)) {
                symptom.removeDateOccurred(newDate);
                symptomRepository.save(symptom);
            }
        }

        return "redirect:/home/{date}";
    }

}
