package com.example.admin.controller;

import com.example.admin.DTO.MemberDTO.*;
import com.example.admin.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.example.admin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "아이디와 비밀번호, 닉네임을 입력하고 회원가입 시도", tags=("user"),
        responses = {@ApiResponse(responseCode = "201", description = "생성 성공 후 토큰 반환"),
                    @ApiResponse(responseCode = "409", description = "중복아이디로 인한 생성 실패")}
    )
    @PostMapping("/member/add")
    public ResponseEntity<String> signUp(@RequestBody MemberCreateRequest request){
        Member member = memberService.signUp(request.getUserId(), request.getPassword(), request.getNickname());
        if(member == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 존재하는 아이디");
        String token = memberService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberLoginRequest request) {
        return memberService.login(request.getUserId(),request.getPassword());
    }

    @Operation(summary = "회원정보찾기", description = "user와 user의 정보 찾기", tags = ("user"))
    @GetMapping("/member/{userId}")
    public MemberResponse getMember (@Parameter(description = "사용자 ID", example = "user_Id") @PathVariable("userId") String userId) {
        Member member = memberService.findByUserId(userId);
        return new MemberResponse(member.getUserId(), member.getNickname());
    }

    @PutMapping("/member")
    public MemberResponse changeMemberName(@RequestBody MemberUpdateRequest request) {
        Member findMember  = memberService.changeName(request.getToken(), request.getNickname());
        return new MemberResponse(findMember.getUserId(), findMember.getNickname());
    }

    @DeleteMapping("/member")
    public void deleteMember(@RequestBody MemberDeleteRequest request) {
        memberService.deleteMember(request.getToken());
    }
}
