package nl.mesoplz.hueapp.main.controllers;

import javafx.scene.effect.Light;
import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static nl.mesoplz.hueapp.main.controllers.HueController.lightsThread;

@Controller
public class PageController {


    @GetMapping("/")
    public String colorPage(Model model) {

        int minDelay = LightsThread.getMinDelay();
        int maxDelay = LightsThread.getMaxDelay();

        model.addAttribute("colors", LightsThread.getMColors());
        model.addAttribute("themes", LightsThread.getThemes());
        model.addAttribute("minDelay", minDelay);
        model.addAttribute("maxDelay", maxDelay);
        model.addAttribute("flowRunning", lightsThread.isRunning());
        model.addAttribute("scheduleRunning", Scheduler.isRunning());
        return "index";
    }

    @GetMapping("/credentials")
    public String credentialsPage(Model model) {
        String ip = LightsThread.getIp();
        String user = LightsThread.getUser();

        model.addAttribute("ip", ip);
        model.addAttribute("user", user);
        model.addAttribute("flowRunning", lightsThread.isRunning());
        model.addAttribute("scheduleRunning", Scheduler.isRunning());
        return "credentials";
    }

    @GetMapping("/scheduler")
    public String schedulerPage(Model model) {
        String onHour = Integer.toString(Scheduler.getTurnOnHour());
        String onMinute = Integer.toString(Scheduler.getTurnOnMinute());
        String offHour = Integer.toString(Scheduler.getTurnOffHour());
        String offMinute = Integer.toString(Scheduler.getTurnOffMinute());

        if (onHour.length() == 1) {
            onHour = "0" + onHour;
        }
        if (onMinute.length() == 1) {
            onMinute = "0" + onMinute;
        }

        if (offHour.length() == 1) {
            offHour = "0" + offHour;
        }
        if (offMinute.length() == 1) {
            offMinute = "0" + offMinute;
        }

        String onTime = onHour + ":" + onMinute;
        String offTime = offHour + ":" + offMinute;

        model.addAttribute("on", onTime);
        model.addAttribute("off", offTime);
        model.addAttribute("flowRunning", lightsThread.isRunning());
        model.addAttribute("scheduleRunning", Scheduler.isRunning());
        model.addAttribute("excludeWeekend", Scheduler.getExcludeWeekends());
        return "scheduler";
    }

}
