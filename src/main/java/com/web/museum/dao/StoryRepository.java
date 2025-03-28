package com.web.museum.dao;

import com.web.museum.entity.Achievement;
import com.web.museum.entity.Story;
import com.web.museum.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    Page<Story> findAll(Pageable pageable);
}
