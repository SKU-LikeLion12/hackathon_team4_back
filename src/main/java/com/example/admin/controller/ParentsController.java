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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> signUp(@RequestBody ParentsCreateRequest request){
        Parents Parents = parentsService.signUp(request.getUserId(), request.getPassword(), request.getNickname(), request.getPhoneNumber(), request.getEmail());
        if(Parents == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 존재하는 아이디");
        String token = parentsService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody ParentsLoginRequest request) {
        return parentsService.login(request.getUserId(),request.getPassword());
    }

    @Operation(summary = "회원정보찾기", description = "user의 정보 찾기", tags = ("user"))
    @GetMapping("/parents/{userId}")
    public ResponseParents getMember (@Parameter(description = "사용자 ID", example = "user_Id") @PathVariable("userId") String userId) {
        Parents parents = parentsService.findByUserId(userId);
        return new ResponseParents(parents);
    }

    @PutMapping("/parents/info") //적당한 url 작성필요(사용자의 정보를 수정하는 기능)
    public ResponseParents changeMemberInfo(@RequestBody ParentsUpdateRequest request) {
        Parents findParents  = parentsService.changeInfo(request.getToken(), request.getNickname(), request.getPassword(), request.getPhoneNumber(), request.getEmail());
        return new ResponseParents(findParents);
    }

    @DeleteMapping("/parents")
    public void deleteMember(@RequestBody ParentsDeleteRequest request) {
        parentsService.deleteParents(request.getToken());
    }

    @GetMapping("/parents/child/{parentsid}")
    public List<ResponsePersonChild> getChlids(@PathVariable("parentsid") Long parentsid){
        List<ResponsePersonChild> responseChilds = new ArrayList<>();
        for(PersonChild personChild : personChildService.findChildByParentId(parentsid)) {
            responseChilds.add(new ResponsePersonChild(personChild));
        }
        return responseChilds;
    }
}
