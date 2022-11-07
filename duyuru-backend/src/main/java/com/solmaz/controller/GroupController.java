package com.solmaz.controller;

import com.solmaz.dto.response.ErrorResponse;
import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.service.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("api/v1/group")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/groupDetail")
    public ResponseEntity<GroupResponse> groupDetail(@RequestParam String groupId) {
        return ResponseEntity.ok(groupService.groupDetail(groupId));
    }
    @PostMapping("/groupPosts")
    public ResponseEntity<List<PostResponse>> groupPosts(@RequestParam String groupId) {
        return ResponseEntity.ok(groupService.groupPosts(groupId));
    }
    @DeleteMapping("/removeGroup")
    @ResponseStatus(HttpStatus.OK)
    public void removeGroup(@RequestParam String groupId) {
        groupService.removeGroup(groupId);
    }
    @PostMapping("/createEmptyGroup")
    @ResponseStatus(HttpStatus.OK)
    public GroupResponse createEmptyGroup(@RequestParam String groupName){
        return groupService.createEmptyGroup(groupName);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleBookmarkException(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }

}
