package com.web.museum.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    private String writer;
    @Column( columnDefinition = "LONGTEXT")
    private String description;
    private int views;
    private int saves;
    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @OneToMany(
            mappedBy = "news",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> listComments;

    @OneToMany(mappedBy = "news",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> listImages;
}