package com.example.finder.dao;

import com.example.finder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u " +
            "WHERE u.id <> ?1 " +
            "AND u.id NOT IN (SELECT liked_user_id FROM user_likes WHERE user_id = ?1) " +
            "AND u.id NOT IN (SELECT disliked_user_id FROM user_dislikes WHERE user_id = ?1) " +
            "AND ?1 NOT IN (SELECT disliked_user_id FROM user_dislikes WHERE user_id = u.id) " +
            "ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<User> findFirstRandomUserNotInLikesOrDislikes(Integer userId);

}