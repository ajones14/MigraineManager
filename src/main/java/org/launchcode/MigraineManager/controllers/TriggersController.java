package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.TriggerRepository;
import org.launchcode.MigraineManager.models.Trigger;
import org.launchcode.MigraineManager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("triggers")
public class TriggersController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private TriggerRepository triggerRepository;

    @GetMapping
    public String displayTriggers(Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);

        model.addAttribute(new Trigger());

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        triggerList.sort(Comparator.comparing(Trigger::getName));
        model.addAttribute("triggerList", triggerList);
        return "main/triggers";
    }

    @PostMapping(params = "addTrigger")
    public String processAddTriggerForm(HttpSession session, @ModelAttribute @Valid Trigger trigger, Errors errors, Model model) {
        User currentUser = authenticationController.getUserFromSession(session);
        trigger.setUserId(currentUser.getId());

        List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());
        triggerList.sort(Comparator.comparing(Trigger::getName));
        model.addAttribute("triggerList", triggerList);

        if (trigger.getName() == null || trigger.getName().isEmpty()) {
            errors.rejectValue("name", "field.invalid", "Please enter a valid trigger");
            return "main/triggers";
        } else {
            String str = trigger.getName();
            String capitalized = str.substring(0, 1).toUpperCase() + str.substring(1);
            trigger.setName(capitalized);
        }

        triggerRepository.save(trigger);
        return "redirect:/triggers";
    }


    @PostMapping(params = "deleteTrigger")
    public String processDeleteTriggerForm(HttpSession session, @RequestParam(value = "resultList", required = false) List<String> resultList) {
        try {
            User currentUser = authenticationController.getUserFromSession(session);
            List<Trigger> triggerList = triggerRepository.findAllByUserId(currentUser.getId());

            if (resultList == null || resultList.isEmpty()) {
                return "redirect:/triggers";
            }

            for (String result : resultList) {
                for (Trigger trigger : triggerList) {
                    if (result.equals(trigger.getName())) {
                        triggerRepository.delete(trigger);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "redirect:/triggers";
    }

}
