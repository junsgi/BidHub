package com.example.bidhub.member;

import com.example.bidhub.dto.LoginDTO;
import com.example.bidhub.dto.MyItemDTO;
import com.example.bidhub.dto.SignUpDTO;
import com.example.bidhub.dto.UpdateDTO;
import com.example.bidhub.global.ResponseDTO;
import com.example.bidhub.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;
    private final TokenService tokenService;
    @PostMapping(path = "/signup")
    public ResponseDTO signUp(@RequestBody SignUpDTO request) {
        return service.signUp(request);
    }

    @PostMapping(path = "/login")
    public ResponseDTO login(@RequestBody LoginDTO request){
        return service.login(request);
    }

    @GetMapping(path = "/{memId}")
    public List<MyItemDTO> getMyItem(@PathVariable("memId") String memId) {
        return service.getMyItem(memId);
    }

    @PatchMapping(path = "/update/id")
    public ResponseDTO updateId(@RequestBody UpdateDTO request) {
        return service.updateId(request);
    }

    @PatchMapping(path = "/update/nickname")
    public ResponseDTO updateNickname(@RequestBody UpdateDTO request) {
        return service.updateNickname(request);
    }

    @PostMapping(path = "/update/passwd")
    public ResponseDTO updatePw(@RequestBody UpdateDTO request) {
        return service.updatePw(request);
    }

    @DeleteMapping(path = "/without")
    public ResponseDTO deleteMem(@RequestBody Map<String, Object> request){
        return service.deleteMem(request);
    }
}
