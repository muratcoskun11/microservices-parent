package com.solmaz.userservice.repository;

import com.solmaz.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findAllByFullNameStartsWithIgnoreCase(String fullname);
    Optional<User> findByEmail(String email);
    @Query(value = "select u.photoUrl from User u where u.userId=?1")
    Optional<String> getPhotoUrlById(String userId);

    boolean existsByEmail(String email);
}
