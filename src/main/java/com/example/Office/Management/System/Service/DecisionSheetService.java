package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.DecisionSheet;
import com.example.Office.Management.System.Repository.DecisionSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecisionSheetService {

    @Autowired
    private DecisionSheetRepository decisionSheetRepository;

    public DecisionSheet addDecision(DecisionSheet decisionSheet) {
        return decisionSheetRepository.save(decisionSheet);
    }

    public List<DecisionSheet> getAllDecisions() {
        return decisionSheetRepository.findAll();
    }

    public Optional<DecisionSheet> getDecisionById(Long id) {
        return decisionSheetRepository.findById(id);
    }

    public DecisionSheet updateDecision(Long id, DecisionSheet updatedDecision) {
        return decisionSheetRepository.findById(id).map(decision -> {
            decision.setSubject(updatedDecision.getSubject());
            decision.setDetails(updatedDecision.getDetails());
            decision.setDecisionDate(updatedDecision.getDecisionDate());
            decision.setStatus(updatedDecision.getStatus());
            decision.setDecisionOutcome(updatedDecision.getDecisionOutcome());
            return decisionSheetRepository.save(decision);
        }).orElseThrow(() -> new RuntimeException("Decision not found"));
    }

    public void deleteDecision(Long id) {
        decisionSheetRepository.deleteById(id);
    }
}
