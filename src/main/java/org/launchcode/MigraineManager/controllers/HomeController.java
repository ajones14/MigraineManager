package org.launchcode.MigraineManager.controllers;

import org.launchcode.MigraineManager.data.MigraineRepository;
import org.launchcode.MigraineManager.data.SymptomRepository;
import org.launchcode.MigraineManager.data.TriggerRepository;
import org.launchcode.MigraineManager.models.Migraine;
import org.launchcode.MigraineManager.models.Symptom;
import org.launchcode.MigraineManager.models.Trigger;
import org.launchcode.MigraineManager.models.User;
import org.launchcode.MigraineManager.models.WeatherAPI.*;
import org.launchcode.MigraineManager.models.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("home")
@SessionAttributes("migraine")
public class HomeController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MigraineRepository migraineRepository;

    @Autowired
    private TriggerRepository triggerRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    public final String weatherAPIKey = "63a9401b41ae4b5da5a74736200604";

    private CurrentWeather callWeatherAPICurrentDay(String key, String zip) {
        RestTemplate restTemplate = new RestTemplate();
        CurrentWeather currentWeather = restTemplate.getForObject("http://api.weatherapi.com/v1/current.json?key=" + key + "&q=" + zip, CurrentWeather.class);
        System.out.println(currentWeather.toString());
        return currentWeather;
    }

    private HistoryWeather callWeatherAPIPastDay(String key, String zip, String date) {
        RestTemplate restTemplate = new RestTemplate();
        HistoryWeather historyWeather = restTemplate.getForObject("http://api.weatherapi.com/v1/history.json?key=" + key + "&q=" + zip + "&dt=" + date, HistoryWeather.class);
        return historyWeather;
    }

    private ForecastWeather callWeatherAPIFutureDay(String key, String zip) {
        RestTemplate restTemplate = new RestTemplate();
        ForecastWeather forecastWeather = restTemplate.getForObject("http://api.weatherapi.com/v1/forecast.json?key=" + key + "&q=" + zip + "&days=3", ForecastWeather.class);
        return forecastWeather;
    }

    public boolean testZipIsInvalid(String key, String zip) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://api.weatherapi.com/v1/search.json?key=" + key + "&q=" + zip, String.class);
        return response.getBody().equals("[]");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu");

    private DateTimeFormatter callFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

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

        String todaydate = LocalDate.now().format(formatter);

        if (todaydate.equals(date)) {
            CurrentWeather currentWeather = callWeatherAPICurrentDay(weatherAPIKey, currentUser.getZipCode());
            Current current = currentWeather.getCurrent();
            WeatherDTO weatherDTO = new WeatherDTO(currentWeather.getLocation().getName(), currentWeather.getLocation().getRegion(),
                    current.getTemp_f(), current.getWind_mph(), current.getHumidity(), current.getPressure_in(), current.getCondition().getIcon());
            model.addAttribute("weatherDTO", weatherDTO);
        } else if (newDate.compareTo(LocalDate.now()) > 0 && newDate.compareTo(LocalDate.now()) <= 3) {
            ForecastWeather forecastWeather = callWeatherAPIFutureDay(weatherAPIKey, currentUser.getZipCode());
            Day day = forecastWeather.getForecast().getSelectedForecastday(newDate.format(callFormatter)).getDay();
            WeatherDTO weatherDTO = new WeatherDTO(forecastWeather.getLocation().getName(), forecastWeather.getLocation().getRegion(),
                    day.getAvgtemp_f(), day.getMaxwind_mph(), day.getAvghumidity(), 0, day.getCondition().getIcon());
            model.addAttribute("weatherDTO", weatherDTO);
        } else if (newDate.compareTo(LocalDate.now()) >= -6 && newDate.compareTo(LocalDate.now()) < 0) {
            HistoryWeather historyWeather = callWeatherAPIPastDay(weatherAPIKey, currentUser.getZipCode(), newDate.format(callFormatter));
            Day day = historyWeather.getForecast().getForecastday().get(0).getDay();
            WeatherDTO weatherDTO = new WeatherDTO(historyWeather.getLocation().getName(), historyWeather.getLocation().getRegion(),
                    day.getAvgtemp_f(), day.getMaxwind_mph(), day.getAvghumidity(),
                    historyWeather.getForecast().getForecastday().get(0).calculateAveragePressure(),  day.getCondition().getIcon());
            model.addAttribute("weatherDTO", weatherDTO);
        } else {
            model.addAttribute("message", "Weather for " + currentUser.getZipCode() + " not available for selected date");
        }

        List<Migraine> migraineList = migraineRepository.findAllByUserId(currentUser.getId());
        if (migraineList == null || migraineList.isEmpty()) {
            session.setAttribute("migraine", new Migraine());
            model.addAttribute("migraine", null);
        } else {
            for (Migraine migraine : migraineList) {
                if (migraine.getEndTime() == null) {
                    model.addAttribute("migraine", migraine);
                } else {
                    session.setAttribute("migraine", new Migraine());
                    model.addAttribute("migraine", null);
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

    @PostMapping(value = "{date}")
    public String startMigraine(@PathVariable("date") String date, @ModelAttribute("migraine") Migraine migraine, Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        model.addAttribute("user", currentUser);
        LocalDate newDate = LocalDate.now();;
        LocalTime currentTime = LocalTime.now();
        LocalDateTime startOrEndTime = newDate.atTime(currentTime);

       if (migraine.getStartTime() == null) {
            migraine.setUserId(currentUser.getId());
            migraine.setStartTime(startOrEndTime);
            migraineRepository.save(migraine);
            return "redirect:/home/{date}";
        } else {
            migraine.setEndTime(startOrEndTime);
            migraineRepository.save(migraine);
            session.removeAttribute("migraine");
            return "redirect:/home/{date}";
        }
    }

    @PostMapping(value = "{date}/cancel")
    public String cancelMigraine(@PathVariable("date") String date, @ModelAttribute("migraine") Migraine migraine, Model model, HttpSession session) {
        User currentUser = authenticationController.getUserFromSession(session);
        model.addAttribute("user", currentUser);
        migraineRepository.delete(migraine);
        return "redirect:/home/{date}";
    }

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
