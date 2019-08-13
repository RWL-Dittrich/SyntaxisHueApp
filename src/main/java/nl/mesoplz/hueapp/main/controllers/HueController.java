package nl.mesoplz.hueapp.main.controllers;

import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;

@Controller
@RequestMapping("/hue")
public class HueController {

    public static LightsThread lightsThread = new LightsThread();

    @PostMapping("/color")
    public String colorPost(String color1, String color2, String color3, int minDelay, int maxDelay) {
        System.out.println(color1 + " | " + color2 + " | " + color3);
        Color c1 = Color.decode(color1);
        Color c2 = Color.decode(color2);
        Color c3 = Color.decode(color3);

        try {
            lightsThread.start(c1, c2, c3, minDelay, maxDelay);
        } catch (Exception e) {
            return "redirect:/";
        }
        return "redirect:/";
    }

    @PostMapping("/credentials")
    public String credentialsPost(String ip, String user) {
        lightsThread.stop();
        LightsThread.setIp(ip);
        LightsThread.setUser(user);
        try {
            LightsThread.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/credentials";
    }

    @PostMapping("/scheduler")
    public String schedulerPost(String on, String off) {
        Scheduler.updateTime(on, off);
        //Wait a little bit for the LightsThread to restart if it needs to so the website displays the right status
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/scheduler";
    }

    @GetMapping("/schedule_start")
    public String schedule_start() {
        Scheduler.stopScheduler();
        Scheduler.scheduleTasks();
        //Wait a little bit for the LightsThread to restart if it needs to so the website displays the right status
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/scheduler";
    }

    @GetMapping("/schedule_stop")
    public String schedule_stop() {
        Scheduler.stopScheduler();
        lightsThread.stop();
        return "redirect:/scheduler";
    }

    @GetMapping("/off")
    public String turnOff() {
        lightsThread.stop();
        return "redirect:/";
    }
}
