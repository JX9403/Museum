package com.web.museum.controller;

import com.web.museum.entity.Wishlist;
import com.web.museum.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam int userId, @RequestParam(required = false) Integer newsId, @RequestParam(required = false) Integer storyId, @RequestParam(required = false) Integer workId) {
        Wishlist wishlist = wishlistService.addWishlist(userId, newsId, storyId, workId);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromWishlist(@RequestParam int userId, @RequestParam(required = false) Integer newsId, @RequestParam(required = false) Integer storyId, @RequestParam(required = false) Integer workId) {
        wishlistService.removeWishlist(userId, newsId, storyId, workId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistByUserId(@PathVariable int userId, @RequestParam(required = false) String type) {
        List<Wishlist> wishlists;
        switch (type) {
            case "news":
                wishlists = wishlistService.getWishlistByUserIdAndNewsIsNotNull(userId);
                break;
            case "story":
                wishlists = wishlistService.getWishlistByUserIdAndStoryIsNotNull(userId);
                break;
            case "work":
                wishlists = wishlistService.getWishlistByUserIdAndWorkIsNotNull(userId);
                break;
            default:
                wishlists = wishlistService.getWishlistByUserId(userId);
                break;
        }
        return new ResponseEntity<>(wishlists, HttpStatus.OK);
    }
}