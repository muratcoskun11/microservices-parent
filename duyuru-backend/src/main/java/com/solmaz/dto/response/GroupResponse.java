package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private String groupId;
    private String name;
    private List<UserResponse> userResponseList;
}
