package com.example.admin.service;

import com.example.admin.domain.Parents;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.admin.domain.PersonChild;
import com.example.admin.repository.PersonChildRepository;
import org.springframework.transaction.annotation.Transactional;

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
        PersonChild newChild = new PersonChild(name, gender, birthDate, height, weight, uniqueKey, parent);
        personChildRepository.save(newChild);
        return newChild;
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


}
