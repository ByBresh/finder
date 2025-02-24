package com.example.finder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] profilePicture;

    @Column(name = "pending_match", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean pendingMatch = false;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_user_id")
    )
    private Set<User> likedUsers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedUsers")
    private Set<User> likedByUsers = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_dislikes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "disliked_user_id")
    )
    private Set<User> dislikedUsers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "dislikedUsers")
    private Set<User> dislikedByUsers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMatch> matchesUser1 = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMatch> matchesUser2 = new HashSet<>();

    @JsonIgnore
    @Transient
    public Set<UserMatch> getAllMatches() {
        Set<UserMatch> allMatches = new HashSet<>();
        allMatches.addAll(matchesUser1);
        allMatches.addAll(matchesUser2);
        return allMatches;
    }

    @JsonIgnore
    @Transient
    public Set<User> getAllMatchedUsers() {
        return getAllMatches().stream().map(match -> match.getOtherUser(this)).collect(Collectors.toSet());
    }

    @Transient
    public String getProfilePictureBase64() {
        return Base64.getEncoder().encodeToString(profilePicture);
    }

    public User(String name, String email, String bio, String password, byte[] profilePicture) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getPendingMatch() {
        return pendingMatch;
    }

    public void setPendingMatch(Boolean pendingMatch) {
        this.pendingMatch = pendingMatch;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Set<User> getDislikedUsers() {
        return dislikedUsers;
    }

    public void setDislikedUsers(Set<User> dislikedUsers) {
        this.dislikedUsers = dislikedUsers;
    }

    public Set<User> getDislikedByUsers() {
        return dislikedByUsers;
    }

    public void setDislikedByUsers(Set<User> dislikedByUsers) {
        this.dislikedByUsers = dislikedByUsers;
    }

    public Set<UserMatch> getMatchesUser1() {
        return matchesUser1;
    }

    public void setMatchesUser1(Set<UserMatch> matchesUser1) {
        this.matchesUser1 = matchesUser1;
    }

    public Set<UserMatch> getMatchesUser2() {
        return matchesUser2;
    }

    public void setMatchesUser2(Set<UserMatch> matchesUser2) {
        this.matchesUser2 = matchesUser2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}