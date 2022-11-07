package com.solmaz.repository;

import com.solmaz.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarksRepository extends JpaRepository<Bookmark, String> {
    List<Bookmark> findAllByUserUserId(String userId);

}
