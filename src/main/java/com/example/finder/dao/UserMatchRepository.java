package com.example.finder.dao;

import com.example.finder.model.UserMatch;
import com.example.finder.model.UserMatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatch, UserMatchId> {
}
