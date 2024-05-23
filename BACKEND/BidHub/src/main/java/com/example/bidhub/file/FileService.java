package com.example.bidhub.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.dir}")
    private String filedir;

    public String uploadFile(MultipartFile file) {
        if (file != null && !file.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            String name = uuid + "_" + file.getOriginalFilename(); // 클라이언트 컴퓨터에 저장되어 있던 사진 이름을 가져옵니다.

            File dest = new File(filedir, name); // 첫 번째 인수 위치에 두 번째 인수 이름을 가진 파일 객체를 생성합니다.
            try {
                file.transferTo(dest); // Transfer the received file to the given destination file.
                return name;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteFile(String name) {
        File file = new File(filedir, name);
        if(file.exists() && file.delete());
    }
    public FileResponse getFile(String name) {
        FileResponse res = new FileResponse();
        try {
            File file = new File(filedir, name);

            // 스프링 프레임워크에서 제공하는 파일을 복사하는 기능들을 제공함.
            res.setBytes(FileCopyUtils.copyToByteArray(file));

            // Files는 정적 메소드로만 구성되어있는 클래스임.
            // Path를 받아 파일의 컨텐츠 구성 요소를 반환합니다.
            res.setContentType(Files.probeContentType(file.toPath()));
        }catch (Exception e) {
        }
        return res;

    }
}
