package com.example.admin.controller;


import com.example.admin.DTO.PersonChildDTO.*;
import com.example.admin.domain.PersonChild;
import com.example.admin.service.ParentsService;
import com.example.admin.service.PersonChildService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PersonChildController {
    private final PersonChildService personChildService;
    private final ParentsService parentsService;
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/child/add")
    public ResponsePersonChild addChild(@RequestBody RequstPersonChild request) {
        PersonChild child = personChildService.createChild(
                request.getName(),
                request.getGender(),
                request.getBirthDate(),
                request.getHeight(),
                request.getWeight(),
                request.getToken()
        );
        return new ResponsePersonChild(child);
    }

    @GetMapping("/child/uniqueKey")
    //findChildByUniqueKey 테스트용
    //uniqueKey하나 들어가는데 굳이 request 만들지 말자
    public ResponsePersonChild findChildByUniqueKey(@RequestBody Map<String, String> uniqueKey) {
//      log.info("UniqueKey : {}", uniqueKey.get("uniqueKey"));
        PersonChild child = personChildService.findChildByUniqueKey(uniqueKey.get("uniqueKey"));
        if (child == null) {
            return null;
        }
        return new ResponsePersonChild(child);
    }

    @PostMapping("/child/login")
    public String login(@RequestBody Map<String, String> uniqueKey) {
        return personChildService.login(uniqueKey.get("uniqueKey"));
    }
}