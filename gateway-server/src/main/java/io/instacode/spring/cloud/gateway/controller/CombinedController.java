package io.instacode.spring.cloud.gateway.controller;

import io.instacode.spring.cloud.gateway.client.book.Book;
import io.instacode.spring.cloud.gateway.client.book.BooksClient;
import io.instacode.spring.cloud.gateway.client.rating.Rating;
import io.instacode.spring.cloud.gateway.client.rating.RatingsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combined")
public class CombinedController {

    private final BooksClient booksClient;
    private final RatingsClient ratingsClient;

    @Autowired
    public CombinedController(BooksClient booksClient, RatingsClient ratingsClient) {
        this.booksClient = booksClient;
        this.ratingsClient = ratingsClient;
    }

    @GetMapping
    public Book getCombinedResponse(@RequestParam Long bookId, @CookieValue("SESSION") String session){
        Book book = booksClient.getBookById(bookId);
        List<Rating> ratings = ratingsClient.getRatingsByBookId(bookId, "SESSION="+session);
        book.setRatings(ratings);
        return book;
    }
}
