package com.example.bidhub.auctionitem;

import com.example.bidhub.domain.AuctionItem;
import com.example.bidhub.dto.AitemsResponse;
import com.example.bidhub.dto.AuctionItemRequest;
import com.example.bidhub.dto.AuctionItemResponse;
import com.example.bidhub.file.FileResponse;
import com.example.bidhub.file.FileService;
import com.example.bidhub.global.ResponseDTO;
import com.example.bidhub.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionItemService {
    private final AuctionItemRepository repository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    public ResponseDTO submit(AuctionItemRequest request) {
        ResponseDTO response = new ResponseDTO();
        AuctionItem entity = null;
        try{
            entity = dtoToEntity(request);
            repository.save(entity);
            response.setStatus(true);
            response.setMessage("경매 등록 성공");
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("다시 시도해주세요");
            fileService.deleteFile(entity.getAitemImg());
        }
        return response;
    }

    public AuctionItemResponse getItem(String aitemId) {
        Optional<AuctionItem> res = repository.findById(aitemId);
        AuctionItem ai = null;
        if (res.isEmpty())
            return AuctionItemResponse.builder().aitemId("null").build();
        ai = res.get();
        return AuctionItemResponse.builder()
                .memId(ai.getMember().getMemId())
                .aitemId(ai.getAitemId())
                .aitemContent(ai.getAitemContent())
                .aitemStart(ai.getAitemStart())
                .aitemTitle(ai.getAitemTitle())
                .aitemBid(ai.getAitemBid())
                .aitemCurrent(ai.getAitemCurrent())
                .aitemImmediate(ai.getAitemImmediate())
                .aitemDate(ai.getAitemDate())
                .aitemImg(ai.getAitemImg())
                .build();
    }

    public List<AitemsResponse> getItems() {
        List<AitemsResponse> res = new LinkedList<>();
        List<AuctionItem> list = repository.findAllByOrderByAitemIdDesc();
        for(AuctionItem item : list) {
            res.add(AitemsResponse.builder()
                    .aitem_id(item.getAitemId())
                    .title(item.getAitemTitle())
                    .current(Long.parseLong(item.getAitemCurrent().trim()))
                    .immediate(item.getAitemImmediate())
                    .remaining(item.getAitemDate().toString())
                    .build()
            );
        }
        return res;
    }

    public List<AitemsResponse> getItems(Integer st, Integer ed) {
        return null;
    }

    public byte[] getImg(String id) {
        Optional<AuctionItem> i = repository.findById(id);
        String name = "";
        byte[] response = null;
        if (i.isPresent()) {
            AuctionItem item = i.get();
            name = item.getAitemImg();
            if (name.equals("null")) name = "bidhub.png";
            response = fileService.getFile(name).getBytes();
        }else {
            response = fileService.getFile("bidhub.png").getBytes();
        }
        return response;
    }
    public AuctionItem dtoToEntity(AuctionItemRequest dto) {
        AuctionItem entity = new AuctionItem();
        String id = LocalDateTime.now().toString() + "_" + repository.getSeq().toString();
        entity.setAitemId(id);
        entity.setAitemBid(dto.getBid());
        entity.setAitemContent(dto.getContent());
        entity.setMember(memberRepository.findById(dto.getUserId()).get());
        entity.setAitemStart(dto.getStart());
        entity.setAitemCurrent(dto.getStart());
        entity.setAitemImmediate(dto.getImmediate());
        entity.setAitemImg(fileService.uploadFile(dto.getImg()));
        entity.setAitemTitle(dto.getTitle());
        String[] date = dto.getDate().split("T");
        Object[] ymd = Arrays.stream(date[0].split("-")).map(Integer::parseInt).toArray();
        Object[] hm = Arrays.stream(date[1].split(":")).map(Integer::parseInt).toArray();
        entity.setAitemDate(LocalDateTime.of((int)ymd[0], (int)ymd[1], (int)ymd[2], (int)hm[0], (int)hm[1]));
        return entity;
    }
}
