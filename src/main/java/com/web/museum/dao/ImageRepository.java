package com.web.museum.dao;

import com.web.museum.entity.Achievement;
import com.web.museum.entity.Comment;
import com.web.museum.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Comment, Integer> {
public Image findById ( int id) ;
}
