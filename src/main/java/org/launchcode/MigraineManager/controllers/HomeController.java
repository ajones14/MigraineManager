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
    public String displayHome(Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        model.addAttribute("user", currentUser);

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        triggerList.sort(Comparator.comparing(Trigger::getName));
        model.addAttribute("triggerList", triggerList);

        LocalDate now = LocalDate.now();
        model.addAttribute("date", now.format(formatter));
        model.addAttribute("forwardDate", now.plusDays(1).format(formatter));
        model.addAttribute("backwardDate", now.minusDays(1).format(formatter));
        return "main/home";
    }

    @GetMapping(value = "{date}")
    public String forwardDate(@PathVariable("date") String date, Model model, HttpSession session) {
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

    @PostMapping(value = "forward/{date}", params = "addTrigger")
    public String processAddTriggerForm(HttpSession session, @RequestParam(value = "resultList", required = false) List<String> resultList) {
        User currentUser = authenticationController.getUserFromSession(session);
        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        LocalDate now = LocalDate.now();

        if (resultList == null || resultList.isEmpty()) {
            return "redirect:/triggers";
        }

        for (String result : resultList) {
            for (Trigger trigger : triggerList) {
                if (result.equals(trigger.getName())) {
                    trigger.addDateOccurred(now);
                    System.out.println(trigger.getDatesOccurred());
                }
            }
        }

        return "redirect:/home";
    }

}
