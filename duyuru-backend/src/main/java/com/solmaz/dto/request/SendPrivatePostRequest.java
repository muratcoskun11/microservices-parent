package com.solmaz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendPrivatePostRequest {
    private String userId;
    private AddPostRequest addPostRequest;
}
