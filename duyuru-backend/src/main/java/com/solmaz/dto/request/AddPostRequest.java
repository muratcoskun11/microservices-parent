package com.solmaz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPostRequest {
    private String topic;
    private String content;
    private List<String> attachements;
    private Set<String> userIdList;
    private Set<String> groupIdList;
}
