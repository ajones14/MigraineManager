package org.launchcode.MigraineManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("symptoms")
public class SymptomsController {

    @GetMapping
    public String symptoms() {
        return "main/symptoms";
    }

}
