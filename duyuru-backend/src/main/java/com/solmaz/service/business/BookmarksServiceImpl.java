package com.solmaz.service.business;

import com.solmaz.dto.response.BookmarkResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.Bookmark;
import com.solmaz.exception.NotFoundException;
import com.solmaz.repository.BookmarksRepository;
import com.solmaz.service.BookMarksService;
import com.solmaz.service.PostService;
import com.solmaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarksServiceImpl implements BookMarksService {
    private final BookmarksRepository bookMarksRepository;

    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;


    private static NotFoundException bookmarkNotFound() {
        return new NotFoundException("Bookmark not found!");
    }

    @Override
    public List<BookmarkResponse> getBookmarks(String userId) {
        var bookmarks = bookMarksRepository.findAllByUserUserId(userId);

        return bookmarks.stream().map(bookmark -> new BookmarkResponse(bookmark.getBookmarkId(), modelMapper.map(bookmark.getPost(),PostResponse.class))).toList();
    }

    @Override
    public BookmarkResponse addBookmarks(String userId, String postId) {
        var user = userService.findById(userId);
        var post = postService.findById(postId);
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setPost(post);
        var savedBookmark = bookMarksRepository.save(bookmark);
        return new BookmarkResponse(savedBookmark.getBookmarkId(),modelMapper.map(savedBookmark.getPost(),PostResponse.class));

    }

    // TODO check bugfix
    @Override
    public void removeBookmarks(String bookmarkId) {
        var bookmark = bookMarksRepository.findById(bookmarkId).orElseThrow(BookmarksServiceImpl::bookmarkNotFound);
        bookMarksRepository.delete(bookmark);
    }
}
