package com.web.museum.dao;

import com.web.museum.entity.Achievement;
import com.web.museum.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUserId(int userId);
    List<Wishlist> findByUserIdAndNewsIsNotNull(int userId);
    List<Wishlist> findByUserIdAndStoryIsNotNull(int userId);
    List<Wishlist> findByUserIdAndWorkIsNotNull(int userId);

    Optional<Wishlist>  findByUserIdAndNewsId( int userId, Integer newsId);
    Optional<Wishlist> findByUserIdAndStoryId( int userId, Integer storyId);
    Optional<Wishlist> findByUserIdAndWorkId( int userId, Integer workId);

}
