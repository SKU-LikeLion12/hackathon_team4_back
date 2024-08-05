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
        Parents parents = parentsService.signUp(request.getUserId(), request.getPassword(), request.getNickname(), request.getPhoneNumber(), request.getEmail());
        if(parents == null) return "이미 존재";
        String token = parentsService.login(request.getUserId(), request.getPassword());
        parents.setToken(token);
//        String token = "제발,,,,"
        return token;
    }

    @PostMapping("/parents/login")
    public String login(@RequestBody ParentsLoginRequest request) {
        String token = parentsService.login(request.getUserId(),request.getPassword());
        return token;
    }

    @Operation(summary = "회원정보찾기", description = "user의 정보 찾기", tags = ("user"))
    @GetMapping("/parents/{userId}")
    public ResponseParents getMember (@Parameter(description = "사용자 ID", example = "user_Id") @PathVariable("userId") String userId) {
        Parents parents = parentsService.findByUserId(userId);
        return new ResponseParents(parents);
    }

    @PutMapping("/parents/info")
    public ResponseParents changeMemberInfo(@RequestBody ParentsUpdateRequest request) {
        Parents findParents  = parentsService.changeInfo(request.getToken(), request.getNickname(), request.getPassword(), request.getPhoneNumber(), request.getEmail());
        return new ResponseParents(findParents);
    }

    @DeleteMapping("/parents")
    public void deleteMember(@RequestBody ParentsDeleteRequest request) {
        parentsService.deleteParents(request.getToken());
    }

    @GetMapping("/parents/child")
    public List<ResponsePersonChild> getChlids(@RequestHeader("Authorization") String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");

        List<ResponsePersonChild> responseChildren = new ArrayList<>();
        Parents parents = parentsService.tokenToParents(token);
        if(parents == null) return null;
        for(PersonChild personChild : personChildService.findChildByParentId(parents.getId())) {
            responseChildren.add(new ResponsePersonChild(personChild));
        }
        return responseChildren;
    }
}
