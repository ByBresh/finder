DROP DATABASE finder;
CREATE DATABASE finder;

USE finder;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_picture LONGBLOB NOT NULL
);

CREATE TABLE user_likes (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    liked_user_id INT NOT NULL
);

CREATE TABLE user_dislikes (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    disliked_user_id INT NOT NULL
);

CREATE TABLE user_matches (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    match_user_id INT NOT NULL
);
