package nl.mesoplz.hueapp.main.controllers;

import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.lights.mColor;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/hue")
public class HueController {

    public static LightsThread lightsThread = new LightsThread();

    @PostMapping("/color")
    public String colorPost(int minDelay, int maxDelay, String[] color) {
        System.out.println(minDelay + " : " + maxDelay);
        System.out.println(Arrays.toString(color) + " : " + color.length);

        ArrayList<mColor> mColors = new ArrayList<>();

        for (String s : color) {
            mColors.add(new mColor(Color.decode(s)));
        }
        lightsThread.getmColors().clear();
        lightsThread.getmColors().addAll(mColors);
        lightsThread.setMinDelay(minDelay);
        lightsThread.setMaxDelay(maxDelay);
        for (String s : color) {
            mColors.add(new mColor(Color.decode(s)));
        }

        return "redirect:/";
    }

    @GetMapping("/color/add")
    public String addColor() {
        if (lightsThread.getmColors().size() < 10) {
            lightsThread.getmColors().add(new mColor(Color.GREEN));
        }
        return "redirect:/";
    }

    @GetMapping("/color/remove")
    public String removeColor() {
        if (lightsThread.getmColors().size() > 1) {
            lightsThread.getmColors().remove(lightsThread.getmColors().size()-1);
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
    public String schedulerPost(String on, String off, boolean excludeWeekend) {
        Scheduler.setExcludeWeekends(excludeWeekend);
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
        lightsThread.stop();
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

    @GetMapping("/on")
    public String turnOn() {
        lightsThread.start();
        return "redirect:/";
    }
}
