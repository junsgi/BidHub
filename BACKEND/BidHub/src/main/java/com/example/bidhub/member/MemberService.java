package com.example.bidhub.member;

import com.example.bidhub.domain.PaymentLog;
import com.example.bidhub.domain.PaymentLogId;
import com.example.bidhub.dto.*;
import com.example.bidhub.domain.Member;
import com.example.bidhub.global.ResponseDTO;
import com.example.bidhub.payment.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final PaymentRepository paymentRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Value("${kakao.payment.cid}")
    private String cid;

    @Value("${kakao.payment.secret_key}")
    private String key;

    private final String kakaoPaymentURL = "https://open-api.kakaopay.com/online/v1/payment/ready";

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
        return repository.findByMemId(memId);
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

    public ResponseDTO point(KakaoPointRequest request){
        ResponseDTO res = new ResponseDTO();
        // client : partner_user_id, total_amount

        request.setCid("TC0ONETIME");
        request.setPartner_order_id(this.getOrderId());
        request.setItem_name("point_recharge");
        request.setQuantity(1);
        request.setTax_free_amount(0);
        request.setApproval_url("http://localhost:3000/approve");
        request.setCancel_url("http://localhost:3000/cancle");
        request.setFail_url("http://localhost:3000/fail");


        System.out.println(request.getTotal_amount());
        try {
            // request 준비 및 요청
            URL url = new URL(kakaoPaymentURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "SECRET_KEY " + key);
            conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨줌

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            wr.write(new Gson().toJson(request));
            wr.flush();

            // response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // response - parse
            JsonParser pr = new JsonParser();
            JsonObject el = pr.parse(result.toString()).getAsJsonObject();

            String tid = el.get("tid").toString();
            String pcURL = el.get("next_redirect_pc_url").toString();
            br.close();
            wr.close();


            // response DB 저장
            PaymentLogId id = new PaymentLogId();
            id.setTid(tid); id.setMemId(request.getPartner_user_id());
            PaymentLog entity = new PaymentLog();
            entity.setPaymentLogId(id);
            paymentRepository.save(entity);

            Optional<Member> otn = repository.findById(request.getPartner_user_id());
            if (otn.isPresent()){
                Member mem = otn.get();
                mem.setMemPoint(mem.getMemPoint() + request.getTotal_amount());
                repository.save(mem);

                res.setStatus(true);
                res.setMessage(pcURL);
            }else {
                res.setStatus(false);
                res.setMessage("존재하지 않는 사용자입니다.");
            }
        }catch (Exception e) {
            res.setStatus(false);
            res.setMessage("오류가 발생했습니다. 다시 시도해 주세요");
        }
        return res;
    }
    public String getOrderId() {
        StringBuilder res = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        res.append(now.getYear());
        if (now.getMonthValue() < 10) res.append("0");
        res.append(now.getMonthValue());
        if (now.getDayOfMonth() < 10) res.append("0");
        res.append(now.getDayOfMonth());
        res.append("_");
        if (now.getHour() < 10) res.append("0");
        res.append(now.getHour());
        if (now.getMinute() < 10) res.append("0");
        res.append(now.getMinute());
        if (now.getSecond() < 10) res.append("0");
        res.append(now.getSecond());
        return res.toString();
    }
}
