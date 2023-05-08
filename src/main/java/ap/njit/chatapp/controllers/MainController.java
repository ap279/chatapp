package ap.njit.chatapp.controllers;

import ap.njit.chatapp.HttpSessionManager;
import ap.njit.chatapp.WebSocketSessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class MainController {

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    private final ApplicationContext context;
    private final WebSocketSessionManager webSocketSessionManager;
    private final HttpSessionManager httpSessionManager;

//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//
//    private Flip flip;
    @GetMapping("/room/{room}")
    public String accessRoom(@PathVariable String room, Model model, HttpSession session) {
        model.addAttribute("room", room);
        String hostName = env.getProperty("server.address");
        String port = env.getProperty("server.port");
        model.addAttribute("hostname", hostName);
        model.addAttribute("port", port);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("connectedSessions", this.getAllConnectedSessions());
        return "room";
    }

//    @GetMapping("/chat/{room}")
//    public String create(@PathVariable String room, Model model) {
//        return "lobby";
//    }

    @Autowired
    public MainController(ApplicationContext context, WebSocketSessionManager webSocketSessionManager, HttpSessionManager httpSessionManager) {
        this.context = context;
        this.webSocketSessionManager = webSocketSessionManager;
        this.httpSessionManager = httpSessionManager;
    }

    @PostMapping("/")
    public String createSession(@RequestParam String username, HttpSession session) {
        session.setAttribute("username", username);
        return "redirect:lobby";
    }

    @GetMapping("/lobby")
    public String lobby(Model model, HttpSession session) {
        String hostName = env.getProperty("server.address");
        String port = env.getProperty("server.port");
        model.addAttribute("hostname", hostName);
        model.addAttribute("port", port);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("connectedSessions", this.getAllConnectedSessions());
        return "lobby";
    }

//    @GetMapping("/flip")
//    public String flip(@RequestParam String message) {
//        this.flip = new Flip(message);
//        String result = this.flip.flip();
//        return result;
//    }

    @GetMapping("/create")
    public String createRoom() {
        return "create";
    }

    @GetMapping("/join")
    public String joinRoom() {
        return "join";
    }


    public Map<String, HttpSession> getAllConnectedHttpSessions() {
        return this.httpSessionManager.sessions;
    };
    public List<String> getAllConnectedSessions() {
        Map<String, WebSocketSession> sessions = this.webSocketSessionManager.sessions;
        List<String> usernames = new ArrayList<>();
        List<Map<String, Object>> allAttrs = new ArrayList<>();
        List<HttpSession> httpSessionObjs = new ArrayList<>();
        for (Map.Entry<String, WebSocketSession> e: sessions.entrySet()){
            String socketId = e.getKey();
            WebSocketSession socketSession = e.getValue();
            Map<String, Object> attrs = socketSession.getAttributes();
            HttpSession httpSessionInstance = (HttpSession) attrs.get("HTTP_SESSION");
            httpSessionObjs.add(httpSessionInstance);
            String username = (String) httpSessionInstance.getAttribute("username");
            usernames.add(username);
            allAttrs.add(attrs);
        };
        return usernames;

    };
}

