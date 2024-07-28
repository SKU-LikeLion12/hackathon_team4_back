package com.example.admin.service;


import com.example.admin.domain.Parents;
import com.example.admin.exception.IdNotFoundException;
import com.example.admin.repository.ParentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentsService {
    private final ParentsRepository parentsRepository;
    private final JwtUtility jwtUtility;

    public Parents tokenToParents(String token){
        return parentsRepository.findByUserId(jwtUtility.validateToken(token).getSubject());
    }

    @Transactional
    public Parents changeInfo(String token, String nickname, String password, String phoneNumber, String email){
        Parents parents = tokenToParents(token);
        if(parents==null) return null;
        parents.setPassword(password);
        parents.setNickname(nickname);
        parents.setPhone_number(phoneNumber);
        parents.setEmail(email);
        return parents;
    }

    @Transactional
    public Parents signUp(String userId, String password, String nickname, String phoneNumber, String email){
        Parents parents = parentsRepository.findByUserId(userId);
        if(parents!=null) return null;
        return parentsRepository.save(new Parents(userId, password, nickname, phoneNumber, email));
    }

    public Parents findById(Long id){
        return parentsRepository.findById(id);
    }

    public String login(String userId, String passwd){
        Parents parents = parentsRepository.findByUserId(userId);
        if(parents!=null && parents.checkPassword(passwd)) {
            return jwtUtility.generateToken(parents.getUser_id());
        }
        return userId;
    }

    public Parents findByUserId(String userId) {
        Parents parents = parentsRepository.findByUserId(userId);
        if(parents==null) throw new IdNotFoundException();
        return parents;
    }

    public List<Parents> findByName(String name){
        return parentsRepository.findByName(name);
    }

    public List<Parents> findAll() {
        return parentsRepository.findAll();
    }

    public boolean deleteParents(String token){
        Parents parents = tokenToParents(token);
        if(parents==null){
            return false;
        }
        parentsRepository.deleteParents(parents);
        return true;
    }
}
