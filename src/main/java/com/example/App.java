package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App
{
    private static final String CHROME_DRIVER_PATH = "C:/tmp/chromedriver-win64/chromedriver.exe";

    public static void main( String[] args )
    {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebElement passwordField = webDriver.findElement(By.id("generatedPassword"));
            String password = passwordField.getAttribute("value");
            System.out.println("Сгенерированный пароль: " + password);

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }

        // Задание №2
        Task2.getIpAddress();

        // Задание №3
        Task3.getWeatherForecast();
    }
}