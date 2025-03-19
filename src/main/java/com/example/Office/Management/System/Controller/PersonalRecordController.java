package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.PersonalRecord;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.PersonalRecordService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/personal-records")
public class PersonalRecordController {

    @Autowired
    private PersonalRecordService personalRecordService;

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String showPersonalRecordForm(Model model) {
        model.addAttribute("personalRecord", new PersonalRecord());
        return "/personalRecord/personal-record-form";
    }

    @PostMapping("/submit")
    public String submitPersonalRecord(@ModelAttribute PersonalRecord personalRecord, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        personalRecord.setUser(user);
        personalRecordService.savePersonalRecord(personalRecord);
        return "redirect:/personal-records/list";
    }

    @GetMapping("/user/{userId}")
    public String getPersonalRecordsByUser(@PathVariable Long userId, Model model) {
        List<PersonalRecord> personalRecords = personalRecordService.getPersonalRecordsByUser(userId);
        model.addAttribute("personalRecords", personalRecords);
        return "/personalRecord/personal-record-list";
    }

    @GetMapping("/list")
    public String listAllPersonalRecords(Model model) {
        List<PersonalRecord> personalRecords = personalRecordService.getAllPersonalRecords();
        model.addAttribute("personalRecords", personalRecords);
        return "/personalRecord/personal-record-list";
    }
}
