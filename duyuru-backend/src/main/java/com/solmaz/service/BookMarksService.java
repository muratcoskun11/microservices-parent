package com.solmaz.service;

import com.solmaz.dto.response.BookmarkResponse;
import com.solmaz.dto.response.PostResponse;

import java.util.List;

public interface BookMarksService {
    List<BookmarkResponse> getBookmarks(String userId);

    BookmarkResponse addBookmarks(String userId, String postId);

    void removeBookmarks(String bookmarkId);
}
