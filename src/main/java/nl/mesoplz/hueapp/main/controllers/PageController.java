package nl.mesoplz.hueapp.main.controllers;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

import static nl.mesoplz.hueapp.main.controllers.HueController.lightsThread;

@Controller
public class PageController {


    @GetMapping("/")
    public String colorPage(Model model) {
        String c1 = String.format("%06X", (0xFFFFFF & lightsThread.getC1().getRGB()));
        String c2 = String.format("%06X", (0xFFFFFF & lightsThread.getC2().getRGB()));
        String c3 = String.format("%06X", (0xFFFFFF & lightsThread.getC3().getRGB()));

        int minDelay = lightsThread.getMinDelay();
        int maxDelay = lightsThread.getMaxDelay();

        model.addAttribute("color1", c1);
        model.addAttribute("color2", c2);
        model.addAttribute("color3", c3);
        model.addAttribute("minDelay", minDelay);
        model.addAttribute("maxDelay", maxDelay);
        model.addAttribute("running", lightsThread.isRunning());
        return "index";
    }

    @GetMapping("/credentials")
    public String credentialsPage(Model model) {
        String ip = lightsThread.getIp();
        String user = lightsThread.getUser();

        model.addAttribute("ip", ip);
        model.addAttribute("user", user);
        model.addAttribute("running", lightsThread.isRunning());
        return "credentials";
    }

}
