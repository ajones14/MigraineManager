package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.MigraineRepository;
import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.data.TriggerRepository;
import org.launchcode.MigraineManager.models.Migraine;
import org.launchcode.MigraineManager.models.Symptom;
import org.launchcode.MigraineManager.models.Trigger;
import org.launchcode.MigraineManager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("stats")
public class StatsController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MigraineRepository migraineRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private TriggerRepository triggerRepository;

    @GetMapping
    public String displayStats(Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        //replace below with something that sorts by most frequent
        Collections.sort(triggerList, Trigger.TriggerDateComparator);
        model.addAttribute("triggerList", triggerList);
        List<String> triggerLabels = new ArrayList<>();
        for (Trigger trigger : triggerList) {
            triggerLabels.add(trigger.getName());
        }
        model.addAttribute("triggerLabels", triggerLabels);

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
//        symptomList.sort(Comparator.comparing(Symptom::getName));
        model.addAttribute("symptomList", symptomList);

        List<Migraine> migraineList = migraineRepository.findAllByUserId(currentUser.getId());
        model.addAttribute("migraineList", migraineList);

        return "main/stats";
    }

}
