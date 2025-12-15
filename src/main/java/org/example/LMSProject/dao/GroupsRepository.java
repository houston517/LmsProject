package org.example.LMSProject.dao;

import org.example.LMSProject.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Group,Long> {
}
