package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task2 {
    private static final String CHROME_DRIVER_PATH = "C:/tmp/chromedriver-win64/chromedriver.exe";

    public static void getIpAddress() {
        //System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver webDriver = new ChromeDriver();

        try {
            webDriver.get("https://api.ipify.org/?format=json");

            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            String ipAddress = (String) jsonObject.get("ip");

            System.out.println("IPv4 адрес: " + ipAddress);

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}