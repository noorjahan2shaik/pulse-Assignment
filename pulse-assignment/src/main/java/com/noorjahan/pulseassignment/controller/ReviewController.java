package com.noorjahan.pulseassignment.controller;

import com.noorjahan.pulseassignment.model.Review;
import com.noorjahan.pulseassignment.service.CapterraScraperService;
import com.noorjahan.pulseassignment.service.G2ScraperService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final G2ScraperService g2ScraperService;
    private final CapterraScraperService capterraScraperService;

    public ReviewController(G2ScraperService g2ScraperService,
                            CapterraScraperService capterraScraperService) {
        this.g2ScraperService = g2ScraperService;
        this.capterraScraperService = capterraScraperService;
    }

    @GetMapping("/scrape")
    public List<Review> scrape(
            @RequestParam String company,
            @RequestParam String source,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        switch (source.toLowerCase()) {
            case "g2":
                return g2ScraperService.scrapeReviews(company, start, end);
            case "capterra":
                return capterraScraperService.scrapeReviews(company, start, end);
            default:
                throw new IllegalArgumentException("Invalid source: " + source);
        }
    }
}

