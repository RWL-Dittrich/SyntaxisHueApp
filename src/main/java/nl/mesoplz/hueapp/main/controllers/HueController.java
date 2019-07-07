package nl.mesoplz.hueapp.main.controllers;

import nl.mesoplz.hueapp.main.lights.LightsThread;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hue")
public class HueController {


    public LightsThread lightsThread = new LightsThread();

    @GetMapping("/on")
    @ResponseBody
    public String turnOn() {
        try {
            lightsThread.start();
        } catch (Exception e) {
            return "Thread already running!";
        }
        return "Turned Hue thread on";
    }

    @GetMapping("/off")
    @ResponseBody
    public String turnOff() {
        lightsThread.stop();
        return "Turned Hue thread off";
    }
}
