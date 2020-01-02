package nl.mesoplz.hueapp.main.controllers;

import javafx.scene.effect.Light;
import nl.mesoplz.hueapp.main.config.ConfigLoader;
import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.lights.MColor;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/hue")
public class HueController {

    public static LightsThread lightsThread = new LightsThread();

    @PostMapping("/color")
    @ResponseBody
    public String colorPost(int minDelay, int maxDelay, String[] color) {
        System.out.println(minDelay + " : " + maxDelay);
        System.out.println(Arrays.toString(color) + " : " + color.length);

        ArrayList<MColor> MColors = new ArrayList<>();

        for (String s : color) {
            MColors.add(new MColor(Color.decode(s)));
        }
        LightsThread.getMColors().clear();
        LightsThread.getMColors().addAll(MColors);
        LightsThread.setMinDelay(minDelay);
        LightsThread.setMaxDelay(maxDelay);
        for (String s : color) {
            MColors.add(new MColor(Color.decode(s)));
        }
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success!";
    }

    @GetMapping("/color/add")
    public String addColor() {
        if (LightsThread.getMColors().size() < 10) {
            LightsThread.getMColors().add(new MColor(Color.GREEN));
        }
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/color/remove")
    public String removeColor() {
        if (LightsThread.getMColors().size() > 1) {
            LightsThread.getMColors().remove(LightsThread.getMColors().size()-1);
        }
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/credentials")
    public String credentialsPost(String ip, String user) {
        lightsThread.stop();
        LightsThread.setIp(ip);
        LightsThread.setUser(user);
        try {
            ConfigLoader.updateConfig();
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
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
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
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
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

    @GetMapping("/themes/load/{id}")
    public String loadTheme(@PathVariable int id) {
        ArrayList<MColor> themeColors = new ArrayList<>();
        for(MColor color : LightsThread.getThemes().get(id).getThemeColors()) {
            themeColors.add(new MColor(color.getColor()));
        }
        LightsThread.setMColors(themeColors);
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/themes/save/{id}")
    public String saveToTheme(@PathVariable int id) {
        ArrayList<MColor> themeColors = new ArrayList<>();
        for(MColor color : LightsThread.getMColors()) {
            themeColors.add(new MColor(color.getColor()));
        }
        LightsThread.getThemes().get(id).setThemeColors(themeColors);
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/themes/remove/{id}")
    public String removeTheme(@PathVariable int id) {
        LightsThread.getThemes().remove(id);
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/themes/add")
    public String addTheme(String name) {
        LightsThread.addTheme(name);
        try {
            ConfigLoader.updateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
