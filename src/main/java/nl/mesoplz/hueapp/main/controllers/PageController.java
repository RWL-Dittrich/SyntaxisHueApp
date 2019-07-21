package nl.mesoplz.hueapp.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static nl.mesoplz.hueapp.main.controllers.HueController.lightsThread;

@Controller
public class PageController {


    @GetMapping("/")
    public String colorPage(Model model) {
        String c1 = String.format("%06X", (0xFFFFFF & lightsThread.getC1().getRGB()));
        String c2 = String.format("%06X", (0xFFFFFF & lightsThread.getC2().getRGB()));
        String c3 = String.format("%06X", (0xFFFFFF & lightsThread.getC3().getRGB()));


        model.addAttribute("value1", c1);
        model.addAttribute("value2", c2);
        model.addAttribute("value3", c3);
        return "index";
    }
}
