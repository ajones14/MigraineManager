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
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

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
        Collections.sort(triggerList, Trigger.TriggerDateComparator);
        List<String> triggerLabels = new ArrayList<>();
        List<Integer> triggerData  = new ArrayList<>();
        for (Trigger trigger : triggerList) {
            if (trigger.getDatesOccurred().size() != 0) {
                triggerLabels.add(trigger.getName());
                triggerData.add(trigger.getDatesOccurred().size());
            }
        }
        model.addAttribute("triggerData", triggerData);
        model.addAttribute("triggerLabels", triggerLabels);

        List<Symptom> symptomList = symptomRepository.findAllByUserId(currentUser.getId());
        Collections.sort(symptomList, Symptom.SymptomDateComparator);
        List<String> symptomLabels = new ArrayList<>();
        List<Integer> symptomData = new ArrayList<>();
        for (Symptom symptom : symptomList) {
            if (symptom.getDatesOccurred().size() != 0) {
                symptomLabels.add(symptom.getName());
                symptomData.add(symptom.getDatesOccurred().size());
            }
        }
        model.addAttribute("symptomLabels", symptomLabels);
        model.addAttribute("symptomData", symptomData);

        List<Migraine> migraineList = migraineRepository.findAllByUserId(currentUser.getId());
        if (migraineList.get(migraineList.size() - 1).getEndTime() == null) {
            migraineList.remove(migraineList.size() - 1);
        }
        migraineList.sort(Comparator.comparing(Migraine::getStartTime));
        HashMap<String, Integer> migraineDaysPerMonth = new LinkedHashMap<>();
        HashMap<String, Integer> migraineData = new LinkedHashMap<>();
        for (Migraine migraine : migraineList) {
            long longDays = DAYS.between(migraine.getStartTime().toLocalDate(), migraine.getEndTime().toLocalDate()) + 1;
            Integer daysBetween = Math.toIntExact(longDays);
            if (migraineData.containsKey(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")))) {
                int count = migraineData.get(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")));
                migraineData.replace(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")), count + 1);

                Integer currentDays = migraineDaysPerMonth.get(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")));
                migraineDaysPerMonth.replace(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")), currentDays + daysBetween);
            } else {
                migraineData.put(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")), 1);
                migraineDaysPerMonth.put(migraine.getStartTime().format(DateTimeFormatter.ofPattern("MMMM uuuu")), daysBetween);
            }
        }

        model.addAttribute("migraineDaysPerMonth", migraineDaysPerMonth.values());
        model.addAttribute("migraineLabels", migraineData.keySet());
        model.addAttribute("migraineFrequency", migraineData.values());

        return "main/stats";
    }

}
