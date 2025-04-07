package com.web.museum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.museum.util.AuthorType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "authors")
@JsonIgnoreProperties({"listWorks", "listStories"})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int id;

    private String name;
    private int birthYear;
    private int deathYear;
    @Column(name = "biography", columnDefinition = "LONGTEXT")
    private String biography;
    @Column(name = "career", columnDefinition = "LONGTEXT")
    private String career;

    @Enumerated(EnumType.STRING) // Lưu kiểu chuỗi (POET, WRITER)
    private AuthorType type; // Kiểu tác giả

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Work> listWorks;

    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Story> listStories;

    @ManyToMany
    @JoinTable(
            name = "author_achievement",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> listAchievements;

    @PreRemove
    private void preRemove() {
        this.listAchievements.clear(); // Clear liên kết trước khi xóa
    }

    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Image> listImages;
}