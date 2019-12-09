package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.repository.MessageRepository;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String submitMessage(Model model, @RequestParam String message) {
        messageRepository.save(message);
        List<String> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "done";
    }
}
