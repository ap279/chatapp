package ap.njit.chatapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String index(Model model) {
        String hostName = env.getProperty("server.address");
        String port = env.getProperty("server.port");
        model.addAttribute("hostname", hostName);
        model.addAttribute("port", port);
        return "index";
    }

}
