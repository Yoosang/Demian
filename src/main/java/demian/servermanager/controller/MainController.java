package demian.servermanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MainController {

    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @RequestMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @RequestMapping("/manager")
    public String managerPage(){
        return "manager";
    }

    @RequestMapping("/staff")
    public String staffPage(){
        return "staff";
    }

}
