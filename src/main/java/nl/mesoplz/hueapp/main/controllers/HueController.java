package nl.mesoplz.hueapp.main.controllers;

import nl.mesoplz.hueapp.main.lights.LightsThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

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

    @GetMapping("/off")
    public String turnOff() {
        lightsThread.stop();
        return "redirect:/";
    }
}
