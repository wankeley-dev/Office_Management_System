package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.PersonalRecord;
import com.example.Office.Management.System.Repository.PersonalRecordRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalRecordService {

    @Autowired
    private PersonalRecordRepository personalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    public PersonalRecord savePersonalRecord(PersonalRecord personalRecord) {
        return personalRecordRepository.save(personalRecord);
    }

    public List<PersonalRecord> getPersonalRecordsByUser(Long userId) {
        return personalRecordRepository.findByUserId(userId);
    }

    public List<PersonalRecord> getAllPersonalRecords() {
        return personalRecordRepository.findAll();
    }

    public Optional<PersonalRecord> getPersonalRecordById(Long id) {
        return personalRecordRepository.findById(id);
    }
}
