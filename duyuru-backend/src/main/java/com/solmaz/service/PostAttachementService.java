package com.solmaz.service;

import com.solmaz.entity.PostAttachement;

import java.io.IOException;
import java.util.List;

public interface PostAttachementService {

    PostAttachement save(PostAttachement attachement);

    List<PostAttachement> saveAll(List<PostAttachement> attachements);

    String saveAttachementToFolder(String id, String attachement) throws IOException;

    String getBase64File(String id) throws IOException;
}
