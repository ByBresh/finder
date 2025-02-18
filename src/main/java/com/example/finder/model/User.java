package com.example.finder.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_user_id")
    )
    private Set<User> likedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "likedUsers")
    private Set<User> likedByUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_dislikes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "disliked_user_id")
    )
    private Set<User> dislikedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "dislikedUsers")
    private Set<User> dislikedByUsers = new HashSet<>();

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMatch> matchesUser1 = new HashSet<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMatch> matchesUser2 = new HashSet<>();

    @Transient
    public Set<UserMatch> getAllMatches() {
        Set<UserMatch> allMatches = new HashSet<>();
        allMatches.addAll(matchesUser1);
        allMatches.addAll(matchesUser2);
        return allMatches;
    }

    public User(String name, String email, String password, byte[] profilePicture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public User() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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