package com.solmaz.service;

import com.solmaz.entity.PostAttachement;
import com.solmaz.entity.PrivatePostAttachement;

import java.io.IOException;
import java.util.List;

public interface PrivatePostAttachementService {

    PrivatePostAttachement save(PrivatePostAttachement attachement);

    List<PrivatePostAttachement> saveAll(List<PrivatePostAttachement> attachements);

    String saveAttachementToFolder(String id, String attachement) throws IOException;

    String getBase64File(String id) throws IOException;
}
