package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("preferences")
public class PreferencesController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String preferences() {
        return "main/preferences";
    }

}
