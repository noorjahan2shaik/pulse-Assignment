package com.noorjahan.pulseassignment.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class SeleniumHelper {

    public WebDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in background
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        return new ChromeDriver(options);
    }
}
