package com.example.admin.service;

import com.example.admin.domain.Parents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.admin.domain.PersonChild;
import com.example.admin.repository.PersonChildRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonChildService {

    private final PersonChildRepository personChildRepository;
    private final ParentsService parentsService;
    private final JwtUtility jwtUtility;

    @Transactional
    public PersonChild createChild(String name, String gender, String birthDate, double height, double weight, String token) {
        Parents parent = parentsService.tokenToParents(token);
        String uniqueKey = generateUniqueKey();
        Long age = calculateAge(birthDate);
        double bmi = calculateBmi(height, weight);

        PersonChild newChild = new PersonChild(name, gender, birthDate, height, weight,bmi,age, uniqueKey, parent);
        newChild.setAge(age);
        newChild.setBmi(bmi);
        personChildRepository.save(newChild);
        return newChild;
    }

    private Long calculateAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birth = LocalDate.parse(birthDate, formatter);
        LocalDate now = LocalDate.now();
        return (long) Period.between(birth, now).getYears();
    }

    private double calculateBmi(double height, double weight) {
        // Convert height from centimeters to meters
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }


    private String generateUniqueKey() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public PersonChild findChildByUniqueKey(String uniqueKey) {
        return personChildRepository.findByUniqueKey(uniqueKey);
    }

    public String login(String uniqueKey) {
        PersonChild child = personChildRepository.findByUniqueKey(uniqueKey);
        if (child != null && child.checkUniqueKey(uniqueKey)) {
            return jwtUtility.ChildGenerateToken(child.getUniqueKey());
        }
        return uniqueKey;
    }

    public List<PersonChild> findChildByParentId(Long userId) {
        Parents parent = parentsService.findById(userId);
        if (parent == null) return null;
        return personChildRepository.findPersonChildById(userId);
    }
    @Transactional
    public Optional<PersonChild> findChildById(Long id){
        return personChildRepository.findById(id);
    }

    public PersonChild tokenToChild(String token) {
        return personChildRepository.findByUniqueKey(jwtUtility.validateToken(token).getSubject());
    }

}
