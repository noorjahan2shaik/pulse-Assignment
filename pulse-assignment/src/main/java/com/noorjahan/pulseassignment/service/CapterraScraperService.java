package com.noorjahan.pulseassignment.service;

import com.noorjahan.pulseassignment.model.Review;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CapterraScraperService implements ReviewScraper {

    private final SeleniumHelper seleniumHelper;

    public CapterraScraperService(SeleniumHelper seleniumHelper) {
        this.seleniumHelper = seleniumHelper;
    }

    @Override
    public List<Review> scrapeReviews(String company, LocalDate start, LocalDate end) {
        List<Review> reviews = new ArrayList<>();
        String url = "https://www.capterra.com/p/" + company.toLowerCase().replace(" ", "-") + "/reviews/";

        WebDriver driver = seleniumHelper.getDriver();
        System.out.println("Scraper started for company: " + company);

        try {
            driver.get(url);
            Thread.sleep(3000); // wait for reviews to load

            List<WebElement> reviewElements = driver.findElements(By.cssSelector(".review-wrapper")); // adjust
            System.out.println("Number of review elements found: " + reviewElements.size());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

            for (WebElement e : reviewElements) {
                String dateStr = e.findElement(By.cssSelector(".review-date")).getText();
                LocalDate reviewDate = LocalDate.parse(dateStr, formatter);
                System.out.println("Review date: " + reviewDate);

                if ((reviewDate.isAfter(start) || reviewDate.isEqual(start)) &&
                    (reviewDate.isBefore(end) || reviewDate.isEqual(end))) {

                    Review r = new Review();
                    r.setTitle(e.findElement(By.cssSelector(".review-title")).getText());
                    r.setDescription(e.findElement(By.cssSelector(".review-text")).getText());
                    r.setReviewer(e.findElement(By.cssSelector(".reviewer-name")).getText());
                    r.setRating(Double.parseDouble(e.findElement(By.cssSelector(".rating")).getAttribute("data-rating")));
                    r.setDate(reviewDate);

                    reviews.add(r);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.quit();
        }

        return reviews;
    }
}
