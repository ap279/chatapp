package ap.njit.chatapp.controllers;

import ap.njit.chatapp.entities.ConnectedSession;
import ap.njit.chatapp.repositories.ConnectedSessionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectedSessionRespository connectedSessionRespository;

    @Autowired
    public ConnectionController(ConnectedSessionRespository connectedSessionRespository) {
        this.connectedSessionRespository = connectedSessionRespository;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewConnection(@RequestParam String username, @RequestParam String sessionId) {
        ConnectedSession c = new ConnectedSession();
        c.setUsername(username);
        c.setSessionId(sessionId);
        connectedSessionRespository.save(c);
        return "Saved";
    }
}
