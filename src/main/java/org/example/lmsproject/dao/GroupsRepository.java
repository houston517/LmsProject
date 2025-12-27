package org.example.lmsproject.dao;

import org.example.lmsproject.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Group,Long> {
}
