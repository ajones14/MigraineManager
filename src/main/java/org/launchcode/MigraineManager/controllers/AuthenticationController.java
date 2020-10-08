package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.*;
import org.launchcode.MigraineManager.models.*;
import org.launchcode.MigraineManager.models.dto.LoginFormDTO;
import org.launchcode.MigraineManager.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
public class AuthenticationController {

    @Autowired
    private HomeController homeController;

    @Autowired
    private TriggerRepository triggerRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        if (homeController.testZipIsInvalid(homeController.weatherAPIKey, registerFormDTO.getZipCode())) {
            errors.rejectValue("zipCode", "field.invalid", "Please enter a valid zip code");
            return "register";
        }

        User newUser = new User(registerFormDTO.getFirstName(), registerFormDTO.getLastName(), registerFormDTO.getUsername(),
                registerFormDTO.getPassword(), registerFormDTO.getZipCode());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        // set default symptomList for new User
        String[] symptomList = new String[]{"Aura", "Confusion", "Difficulty speaking", "Dizziness", "Eye pain", "Distorted vision",
                "Fatigue", "Numbness", "Mood swings", "Memory difficulties", "Sound sensitivity", "Light sensitivity", "Nausea",
                "Yawning", "Vomiting", "Smell sensitivity"};
        for (int i = 0; i < symptomList.length; i++) {
            Symptom symptom = new Symptom(newUser.getId(), symptomList[i]);
            symptomRepository.save(symptom);
        }

        // set default triggerList for new User
        String[] triggerList = new String[]{"Sugar", "Stress", "Dehydration", "Barometric pressure changes", "Humidity changes",
                "Temperature changes", "Strong lighting", "Exercise", "MSG", "Aspartame", "Sleep deprivation", "Alcohol",
                "Caffeine", "Menstruation", "PMS"};
        for (int i = 0; i < triggerList.length; i++) {
            Trigger trigger = new Trigger(newUser.getId(), triggerList[i]);
            triggerRepository.save(trigger);
        }

        //return "redirect:/home";
        return "redirect:/login-success";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            return "login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            return "login";
        }
        setUserInSession(request.getSession(), theUser);
        //return "redirect:/home";
        return "redirect:/login-success";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login-success";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/welcome";
    }

}
