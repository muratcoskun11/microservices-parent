package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberResponse {
    private String groupMemberId;
    private UserResponse member;
    private GroupWithoutMembers group;
}
