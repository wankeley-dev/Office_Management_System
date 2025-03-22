package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.DecisionSheet;
import com.example.Office.Management.System.Service.DecisionSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/decisions")
public class DecisionSheetController {

    @Autowired
    private DecisionSheetService decisionSheetService;

    // Show form to add a new decision
    @GetMapping("/add")
    public String showAddDecisionForm(Model model) {
        model.addAttribute("decisionSheet", new DecisionSheet());
        return "/decisionSheet/decision-sheet-form";
    }

    // Handle form submission for adding a decision
    @PostMapping("/add")
    public String addDecision(@ModelAttribute DecisionSheet decisionSheet) {
        decisionSheetService.addDecision(decisionSheet);
        return "redirect:/decisions/list";
    }

    // Display all decisions
    @GetMapping("/list")
    public String listAllDecisions(Model model) {
        List<DecisionSheet> decisions = decisionSheetService.getAllDecisions();
        model.addAttribute("decisions", decisions);
        return "/decisionSheet/decision-sheet-list";
    }

    // Show edit form for decision
    @GetMapping("/edit/{id}")
    public String editDecision(@PathVariable Long id, Model model) {
        Optional<DecisionSheet> decision = decisionSheetService.getDecisionById(id);
        if (decision.isPresent()) {
            model.addAttribute("decisionSheet", decision.get());
            return "/decisionSheet/decision-sheet-form";
        }
        return "redirect:/decisions/list";
    }

    // Handle update request for decision
    @PostMapping("/update/{id}")
    public String updateDecision(@PathVariable Long id, @ModelAttribute DecisionSheet decisionSheet) {
        decisionSheetService.updateDecision(id, decisionSheet);
        return "redirect:/decisions/list";
    }

    // Delete a decision
    @GetMapping("/delete/{id}")
    public String deleteDecision(@PathVariable Long id) {
        decisionSheetService.deleteDecision(id);
        return "redirect:/decisions/list";
    }
}
