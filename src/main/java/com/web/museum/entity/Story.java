package com.web.museum.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;
    private int views;
    private int saves;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "story",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> listComments;

    @OneToMany(mappedBy = "story",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> listImages;
}