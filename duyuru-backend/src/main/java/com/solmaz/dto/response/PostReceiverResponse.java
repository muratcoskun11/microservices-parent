package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReceiverResponse {
    private String postId;
    private List<UserResponse> userResponseList;
    private List<GroupResponse> groupResponseList;
}
