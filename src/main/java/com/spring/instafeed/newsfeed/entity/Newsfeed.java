package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Newsfeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String imagePath;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean is_deleted;
    private LocalDateTime deletedAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
