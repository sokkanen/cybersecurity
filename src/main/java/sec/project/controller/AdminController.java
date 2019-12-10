package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.repository.MessageRepository;
import sec.project.repository.SignupRepository;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SignupRepository signupRepository;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminLogin(){
        return "adminLogin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String adminView(Model model, @RequestParam String user, @RequestParam String password){
        if (user.equals("admin") && password.equals("password")){
            List<String> messages = messageRepository.findAll();
            model.addAttribute("messages", messages);
            model.addAttribute("signups", signupRepository.findAll());
            return "admin";
        }
        model.addAttribute("error", "failed login");
        return "adminLogin";
    }
}
