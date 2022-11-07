package com.solmaz.repository;

import com.solmaz.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,String> {
    List<Group> findAllByNameStartsWith(String nameStartsWith);
}
