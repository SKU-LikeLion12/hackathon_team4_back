package com.example.admin.controller;

import com.example.admin.DTO.ParentsDTO.*;
import com.example.admin.DTO.PersonChildDTO.*;
import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import com.example.admin.service.ParentsService;
import com.example.admin.service.PersonChildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParentsController {
    private final ParentsService parentsService;
    private final PersonChildService personChildService;

    @Operation(summary = "회원가입", description = "아이디와 비밀번호, 닉네임을 입력하고 회원가입 시도", tags=("user"),
        responses = {@ApiResponse(responseCode = "201", description = "생성 성공 후 토큰 반환"),
                    @ApiResponse(responseCode = "409", description = "중복아이디로 인한 생성 실패")}
    )
    @PostMapping("/parents/add")
    public String signUp(@RequestBody ParentsCreateRequest request){
        Parents parents = parentsService.signUp(
                request.getUserId(),
                request.getPassword(),
                request.getNickname(),
                request.getPhoneNumber(),
                request.getEmail());
        if(parents == null) return "이미 존재";
        return parentsService.login(request.getUserId(), request.getPassword());
    }

    @PostMapping("/parents/login")
    public String login(@RequestBody ParentsLoginRequest request) {
        return parentsService.login(request.getUserId(),request.getPassword());
    }

    @GetMapping("/parents/{userId}")
    public ResponseParents getMember (@PathVariable("userId") String userId) {
        Parents parents = parentsService.findByUserId(userId);
        if(parents == null) return null;
        return new ResponseParents(parents);
    }

    @PutMapping("/parents/info")
    public ResponseParents changeMemberInfo(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ParentsUpdateRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        Parents findParents  = parentsService.changeInfo(
                token,
                request.getNickname(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getEmail());
        return new ResponseParents(findParents);
    }

    @DeleteMapping("/parents")
    public boolean deleteMember(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return parentsService.deleteParents(token);
    }

    @GetMapping("/parents/child")
    public List<ResponsePersonChild> getChlids(
            @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        List<ResponsePersonChild> responseChilds = new ArrayList<>();
        Parents parents = parentsService.tokenToParents(token);
        if(parents == null) return null;
        for(PersonChild personChild : personChildService.findChildByParentsUserId(parents.getUser_id())) {
            responseChilds.add(new ResponsePersonChild(personChild));
        }
        return responseChilds;
    }
}
