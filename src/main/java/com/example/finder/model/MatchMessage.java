package com.example.finder.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_messages")
public class MatchMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user1_id", referencedColumnName = "user1_id", nullable = false),
            @JoinColumn(name = "user2_id", referencedColumnName = "user2_id", nullable = false)
    })
    private UserMatch userMatch;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "sent_at", nullable = false)
    private Timestamp sentAt = Timestamp.valueOf(LocalDateTime.now());

    public MatchMessage(UserMatch userMatch, User sender, String messageText) {
        this.userMatch = userMatch;
        this.sender = sender;
        this.messageText = messageText;
    }

    public MatchMessage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserMatch getUserMatch() {
        return userMatch;
    }

    public void setUserMatch(UserMatch userMatch) {
        this.userMatch = userMatch;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

}
