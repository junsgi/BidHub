package com.example.bidhub.auction;

import com.example.bidhub.domain.AuctionItem;
import com.example.bidhub.dto.AuctionItemRequest;
import com.example.bidhub.dto.AuctionItemResponse;
import com.example.bidhub.file.FileService;
import com.example.bidhub.global.ResponseDTO;
import com.example.bidhub.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository repository;
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


    public AuctionItem dtoToEntity(AuctionItemRequest dto) {
        AuctionItem entity = new AuctionItem();
        String id = LocalDateTime.now().toString() + "_" + repository.getSeq().toString();
        entity.setAitemId(id);
        entity.setAitemBid(dto.getBid());
        entity.setAitemContent(dto.getContent());
        entity.setMember(memberRepository.findById(dto.getUserid()).get());
        entity.setAitemStart(dto.getStart());
        entity.setAitemCurrent(dto.getStart());
        entity.setAitemImmediate(dto.getImmediate());
        entity.setAitemDate(dto.getDate());
        entity.setAitemImg(fileService.uploadFile(dto.getImg()));
        return entity;
    }
}
