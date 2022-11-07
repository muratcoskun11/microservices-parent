package com.solmaz.controller;

import com.solmaz.dto.response.BookmarkResponse;
import com.solmaz.dto.response.ErrorResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.service.BookMarksService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookmark")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BookMarksController {
    private final BookMarksService bookMarksService;
    private final TokenProvider tokenProvider;

    // rootURL= localhost:7070/duyuru/api/v1/bookmark
    @PostMapping("/getBookmarks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookmarkResponse>> getBookmarks() {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(bookMarksService.getBookmarks(userId));
    }
    @PostMapping("/addBookmark")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookmarkResponse> addBookmarks(@RequestParam String postId) {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(bookMarksService.addBookmarks(userId, postId));
    }
    @DeleteMapping("/removeBookmark")
    @ResponseStatus(HttpStatus.OK)
    public void removeBookmarks(@RequestParam String bookmarkId) {

        bookMarksService.removeBookmarks(bookmarkId);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleBookmarkException(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }
}
