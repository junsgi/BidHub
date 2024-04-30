package com.example.bidhub.member;

import com.example.bidhub.dto.LoginDTO;
import com.example.bidhub.dto.MyItemDTO;
import com.example.bidhub.dto.SignUpDTO;
import com.example.bidhub.domain.Member;
import com.example.bidhub.dto.UpdateDTO;
import com.example.bidhub.global.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public ResponseDTO signUp(SignUpDTO request){
        if (request.getNickname().isEmpty()) request.setNickname(request.getId());
        ResponseDTO response = new ResponseDTO();
        Optional<Member> idCheck = repository.findById(request.getId());
        if (idCheck.isPresent()) {
            response.setMessage("사용할 수 없는 아이디입니다. (중복 아이디)");
        }
        else {
            request.setPw(bcryptPasswordEncoder.encode(request.getPw()));
            Member member = SignUpDTO.toMember(request);

            response.setStatus(true);
            response.setMessage("회원가입 완료");
            repository.save(member);
        }
        return response;
    }

    public ResponseDTO login(LoginDTO request){
        ResponseDTO response = new ResponseDTO();
        Optional<Member> member = repository.findById(request.getId());

        if (member.isEmpty() || !bcryptPasswordEncoder.matches(request.getPw(), member.get().getMemPw())){
            response.setMessage("아이디 혹은 비밀번호를 다시 확인해주세요.");
        }
        else {
            response.setStatus(true);
            response.setMessage("로그인 성공");
            response.setNickname(member.get().getMemNickname());
            response.setPoint(member.get().getMemPoint());
        }
        return response;
    }

    public List<MyItemDTO> getMyItem(String memId) {
        List<MyItemDTO> response = new ArrayList<>();

        return response;
    }

    public ResponseDTO updateId(UpdateDTO request) {
        ResponseDTO response = new ResponseDTO();
        Optional<Member> check = repository.findById(request.getAfter());
        if (check.isPresent()) response.setMessage("사용할 수 없는 아이디입니다.");
        else {
            Optional<Member> target = repository.findById(request.getBefore());
            target.get().setMemId(request.getAfter());
            repository.save(target.get());
            response.setStatus(true);
            response.setMessage("아이디 변경 성공");
        }
        return response;
    }

    public ResponseDTO updateNickname(UpdateDTO request) {
        ResponseDTO res = new ResponseDTO();
        Optional<Member> target = repository.findById(request.getId());
        target.get().setMemNickname(request.getAfter());
        repository.save(target.get());

        res.setStatus(true);
        res.setMessage("닉네임 변경에 성공하였습니다.");
        return res;
    }
    public ResponseDTO updatePw(UpdateDTO request) {
        ResponseDTO res = new ResponseDTO();
        Optional<Member> target = repository.findById(request.getId());
        target.get().setMemPw(request.getAfter());
        repository.save(target.get());

        res.setStatus(true);
        res.setMessage("비밀번호 변경에 성공하였습니다.");
        return res;
    }

    public ResponseDTO deleteMem(Map<String, Object> request){
        ResponseDTO res = new ResponseDTO();
        res.setStatus(true);
        res.setMessage("탈퇴 성공");

        repository.deleteById(request.get("id").toString());
        return res;
    }


}
