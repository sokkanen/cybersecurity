package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.repository.AdminPwdRepository;
import sec.project.repository.MessageRepository;
import sec.project.repository.SignupRepository;

@Controller
public class AdminController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SignupRepository signupRepository;

    @Autowired
    AdminPwdRepository adminPwdRepository;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminLogin(){
        return "adminLogin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String adminView(Model model, @RequestParam String user, @RequestParam String password){
        if (user.equals("admin") && password.equals(adminPwdRepository.getAdminPassword())){
            model.addAttribute("messages", messageRepository.findAll());
            model.addAttribute("signups", signupRepository.findAll());
            return "admin";
        }
        model.addAttribute("error", "failed login");
        return "adminLogin";
    }

    @RequestMapping(value = "/adminpwd", method = RequestMethod.POST)
    public String adminView(Model model, @RequestParam String password){
        adminPwdRepository.change(password);
        model.addAttribute("pwd", "true");
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("signups", signupRepository.findAll());
        return "admin";
    }
}
