package com.web.museum.service;

import com.web.museum.dao.*;
import com.web.museum.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private WorkRepository workRepository;

    @Override
    public Wishlist addWishlist(int userId, Integer newsId, Integer storyId, Integer workId ) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        if (newsId != null ) {
            News news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
            wishlist.setNews(news);
        } else if (storyId != null) {
            Story story = storyRepository.findById(storyId).orElseThrow(() -> new RuntimeException("Story not found"));
            wishlist.setStory(story);
        } else if (workId != null) {
            Work work = workRepository.findById(workId).orElseThrow(() -> new RuntimeException("Work not found"));
            wishlist.setWork(work);
        }

        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeWishlist(int userId, Integer newsId, Integer storyId, Integer workId) {
        Wishlist wishlist = null;
        if (newsId != null) {
            wishlist = wishlistRepository.findByUserIdAndNewsId(userId, newsId)
                    .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        } else if (storyId != null) {
            wishlist = wishlistRepository.findByUserIdAndStoryId(userId, storyId)
                    .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        } else if (workId != null) {
            wishlist = wishlistRepository.findByUserIdAndWorkId(userId, workId)
                    .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        }

        if (wishlist != null) {
            wishlistRepository.delete(wishlist);
        }
    }

    @Override
    public List<Wishlist> getWishlistByUserId(int userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public List<Wishlist> getWishlistByUserIdAndNewsIsNotNull(int userId) {
        return wishlistRepository.findByUserIdAndNewsIsNotNull(userId);
    }

    @Override
    public List<Wishlist> getWishlistByUserIdAndStoryIsNotNull(int userId) {
        return wishlistRepository.findByUserIdAndStoryIsNotNull(userId);
    }

    @Override
    public List<Wishlist> getWishlistByUserIdAndWorkIsNotNull(int userId) {
        return wishlistRepository.findByUserIdAndWorkIsNotNull(userId);
    }
}