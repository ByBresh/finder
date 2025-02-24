package com.example.finder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_matches")
public class UserMatch {

    @EmbeddedId
    private UserMatchId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("user1Id")
    @JoinColumn(name = "user1_id")
    private User user1;

    @JsonIgnore
    @ManyToOne
    @MapsId("user2Id")
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "matched_at", nullable = false)
    private Timestamp matchedAt = Timestamp.valueOf(LocalDateTime.now());

    @JsonIgnore
    @OneToMany(mappedBy = "userMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchMessage> messages = new HashSet<>();

    public User getOtherUser(User user) {
        return user.equals(user1) ? user2 : user1;
    }

    public UserMatch(User userA, User userB) {
        if (userA.getId() < userB.getId()) {
            this.user1 = userA;
            this.user2 = userB;
        } else {
            this.user1 = userB;
            this.user2 = userA;
        }

        this.id = new UserMatchId(this.user1.getId(), this.user2.getId());
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

    public Set<MatchMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<MatchMessage> messages) {
        this.messages = messages;
    }

    public List<MatchMessage> getOrderedMessages() {
        List<MatchMessage> orderedMessages = new ArrayList<>(messages);
        orderedMessages.sort(Comparator.comparing(MatchMessage::getSentAt));
        return orderedMessages;
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
