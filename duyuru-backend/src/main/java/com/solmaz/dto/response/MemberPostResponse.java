package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPostResponse {
    private Long id;
    private PostResponse postResponse;
    private GroupMemberResponse groupMemberResponse;

}
