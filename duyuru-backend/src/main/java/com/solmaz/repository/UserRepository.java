package com.solmaz.repository;

import com.solmaz.entity.Group;
import com.solmaz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findAllByFullNameStartsWithIgnoreCase(String fullname);
    @Query(value = "select gm.group from GroupMember gm where gm.user.userId=?1")
    List<Group> findGroupsOfUser(String userId);
    Optional<User> findByEmail(String email);
    @Query(value = "select u.photoUrl from User u where u.userId=?1")
    Optional<String> getPhotoUrlById(String userId);

}
