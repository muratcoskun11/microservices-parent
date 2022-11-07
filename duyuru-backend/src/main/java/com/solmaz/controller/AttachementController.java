package com.solmaz.controller;

import com.solmaz.service.PostAttachementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/attachement")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AttachementController {
    private final PostAttachementService postAttachementService;

    @GetMapping
    public String getFile(@RequestParam String id) throws IOException {
        return postAttachementService.getBase64File(id);
    }

}
