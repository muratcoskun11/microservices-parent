package com.solmaz.service.business;

import com.solmaz.entity.PostAttachement;
import com.solmaz.repository.PostAttachementRepository;
import com.solmaz.service.PostAttachementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PostAttachementServiceImpl implements PostAttachementService {
    @Value("${post.attachements.directory}")
    private String postAttachementsDirectory;
    private final PostAttachementRepository attachementRepository;

    @Override
    public PostAttachement save(PostAttachement attachement){
        return attachementRepository.save(attachement);
    }
    @Override
    public List<PostAttachement> saveAll(List<PostAttachement> attachements){
        return attachementRepository.saveAll(attachements);
    }

    @Override
    public String saveAttachementToFolder(String id, String attachement) throws IOException {
        byte[] content = Base64.getDecoder().decode(attachement);
        var rootPath = System.getProperty("user.dir")+postAttachementsDirectory;
        var filePath = rootPath+"/"+id;
        InputStream is = new ByteArrayInputStream(content);
        is.close();
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path,content, StandardOpenOption.CREATE);
        return path.toString();
    }

    @Override
    public String getBase64File(String id) throws IOException {
        var attachement =attachementRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Attachement not found"));
        var bytes = Files.readAllBytes(Paths.get(attachement.getFilePath()));
        return Base64.getEncoder().encodeToString(bytes);
    }
}
