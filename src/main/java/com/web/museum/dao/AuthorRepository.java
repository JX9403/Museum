package com.web.museum.dao;

import com.web.museum.entity.Author;
import com.web.museum.util.AuthorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a FROM Author a " +
            "WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:type IS NULL OR a.type = :type)")
    Page<Author> searchAuthors(@Param("name") String name,
                               @Param("type") AuthorType type,
                               Pageable pageable);
}
