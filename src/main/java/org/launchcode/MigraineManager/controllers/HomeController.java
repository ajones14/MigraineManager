package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.MigraineRepository;
import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.data.TriggerRepository;
import org.launchcode.MigraineManager.models.Symptom;
import org.launchcode.MigraineManager.models.Trigger;
import org.launchcode.MigraineManager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        triggerList.sort(Comparator.comparing(Trigger::getName));
        model.addAttribute("triggerList", triggerList);

        LocalDate newDate = LocalDate.parse(date, formatter);
        model.addAttribute("date", date);
        model.addAttribute("forwardDate", newDate.plusDays(1).format(formatter));
        model.addAttribute("backwardDate", newDate.minusDays(1).format(formatter));
        return "main/home";
    };

    @PostMapping(value = "{date}/saveTriggers")
    public String processSaveTriggerForm(HttpSession session, @PathVariable("date") String date, @RequestParam(value = "resultList", required = false) List<String> resultList, Model model) {
        User currentUser = authenticationController.getUserFromSession(session);
        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        LocalDate newDate = LocalDate.parse(date, formatter);

        if (resultList == null || resultList.isEmpty()) {
            return "redirect:/home/{date}";
        }

        for (String result : resultList) {
            for (Trigger trigger : triggerList) {
                if (result.equals(trigger.getName())) {
                    trigger.addDateOccurred(newDate);
                    triggerRepository.save(trigger);
                    System.out.println(trigger.getDatesOccurred());
                }
            }
        }

        return "redirect:/home/{date}";
    }

}
