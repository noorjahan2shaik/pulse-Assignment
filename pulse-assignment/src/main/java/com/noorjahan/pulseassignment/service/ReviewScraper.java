package com.noorjahan.pulseassignment.service;

import com.noorjahan.pulseassignment.model.Review;
import java.time.LocalDate;
import java.util.List;

public interface ReviewScraper {
    List<Review> scrapeReviews(String company, LocalDate start, LocalDate end);
}