package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.Signup;
import sec.project.repository.MessageRepository;
import sec.project.repository.SignupRepository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class SignupController {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        logger.info("New Signup added");
        List<String> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "done";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String requestInfo(Model model, @RequestParam String name) {
        model.addAttribute("signups", signupRepository.findSignup(name));
        List<String> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "done";
    }
}
