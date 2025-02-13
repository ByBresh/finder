package com.example.finder.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_matches")
public class UserMatch {

    @EmbeddedId
    private UserMatchId id;

    @ManyToOne
    @MapsId("user1Id")
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @MapsId("user2Id")
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "matched_at", nullable = false)
    private Timestamp matchedAt = Timestamp.valueOf(LocalDateTime.now());

    @OneToMany(mappedBy = "userMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchMessage> messages = new ArrayList<>();

    public UserMatch(User user1, User user2) {
        this.id = new UserMatchId(user1.getId(), user2.getId());
        this.user1 = user1;
        this.user2 = user2;
    }

    public UserMatch() {
    }

    public UserMatchId getId() {
        return id;
    }

    public void setId(UserMatchId id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Timestamp getMatchedAt() {
        return matchedAt;
    }

    public void setMatchedAt(Timestamp matchedAt) {
        this.matchedAt = matchedAt;
    }

    public List<MatchMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<MatchMessage> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserMatch userMatch = (UserMatch) o;
        return Objects.equals(id, userMatch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
