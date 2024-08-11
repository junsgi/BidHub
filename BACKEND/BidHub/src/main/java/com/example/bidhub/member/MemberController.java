package com.example.bidhub.member;

import com.example.bidhub.dto.*;
import com.example.bidhub.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;
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

    @PostMapping(path = "/update/nickname")
    public ResponseDTO updateNickname(@RequestBody UpdateDTO request) {
        return service.updateNickname(request);
    }

    @PostMapping(path = "/update/passwd")
    public ResponseDTO updatePw(@RequestBody UpdateDTO request) {
        return service.updatePw(request);
    }

    @DeleteMapping(path = "/without/{id}") // { "id" : string }
    public ResponseDTO deleteMem(@PathVariable("id") String id){
        return service.deleteMem(id);
    }

    @PostMapping(path = "/point")
    public ResponseDTO point(@RequestBody KakaoPointRequest request) {return service.point(request);}

    @PostMapping(path = "/point/approved")
    public ResponseDTO approved(@RequestBody ApprovedRequest request) {
        return service.approved(request);
    }

    @GetMapping(path = "/detail/{id}")
    public UserInfo getPoint(@PathVariable("id") String id) { return service.getUser(id);}


}
