package com.web.museum.service;

import com.web.museum.entity.Wishlist;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WishlistService {
    Wishlist addWishlist (int userId, Integer newsId, Integer storyId, Integer workId);
    void removeWishlist(int userId, Integer newsId, Integer storyId, Integer workId);
    List<Wishlist> getWishlistByUserId(int userId);
    List<Wishlist> getWishlistByUserIdAndNewsIsNotNull(int userId);
    List<Wishlist> getWishlistByUserIdAndStoryIsNotNull(int userId);
    List<Wishlist> getWishlistByUserIdAndWorkIsNotNull(int userId);
}
