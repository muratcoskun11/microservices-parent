package com.solmaz.controller;


import com.solmaz.dto.request.AddGroupMemberRequest;
import com.solmaz.dto.request.AddGroupRequest;
import com.solmaz.dto.response.*;
import com.solmaz.service.GroupMembersService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
@RestController
@RequestMapping("api/v1/GroupMembers")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class GroupMembersController {
    private final GroupMembersService groupMembersService;
    private final TokenProvider tokenProvider;

    @PostMapping("/getGroupMembers")
    public ResponseEntity<List<UserResponse>> getGroupMembers(@RequestParam String groupId) {
        return ResponseEntity.ok(groupMembersService.getGroupMembers(groupId));
    }
    @PostMapping("/addUserToGroup")
    public ResponseEntity<List<GroupMemberResponse>> addUserToGroup(@RequestBody AddGroupMemberRequest addGroupMemberRequest) {
        return ResponseEntity.ok(groupMembersService.addGroupMember(addGroupMemberRequest));
    }
    @GetMapping("/getUserGroups")
    public ResponseEntity<List<GroupResponse>> getUserGroups() {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(groupMembersService.getUserGroups(userId));
    }
    @PostMapping("/createGroupWithMembers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<GroupMemberResponse>> createGroup(@RequestBody AddGroupRequest addGroupRequest) {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(groupMembersService.createGroupWithMembers(userId, addGroupRequest));
    }
    @PostMapping("/searchUserOrGroup")
    public ResponseEntity<SearchResponse> searchByUsernameStartsWith(@RequestParam String nameStartsWith){
        return ResponseEntity.ok(groupMembersService.searchByUserNameOrGroupNameStartsWith(nameStartsWith));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleBookmarkException(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }
}
