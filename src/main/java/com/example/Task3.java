package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class Task3 {
    private static final String FORECAST_FILE = "result/forecast.txt";
    private static final String CHROME_DRIVER_PATH = "C:/tmp/chromedriver-win64/chromedriver.exe";

    private static final String WEATHER_API_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    public static void getWeatherForecast() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver webDriver = new ChromeDriver();

        try {
            java.nio.file.Files.createDirectories(Paths.get("result/"));

            webDriver.get(WEATHER_API_URL);

            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();

            JSONParser parser = new JSONParser();
            JSONObject weatherData = (JSONObject) parser.parse(jsonString);

            JSONObject hourly = (JSONObject) weatherData.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            try (PrintWriter writer = new PrintWriter(new FileWriter(FORECAST_FILE))) {
                String header = String.format("%-4s | %-20s | %-12s | %-10s",
                        "№", "Дата/время", "Температура", "Осадки (мм)");

                System.out.println(header);
                writer.println("Прогноз погоды для Нижнего Новгорода");
                writer.println();
                writer.println(header);

                for (int i = 0; i < times.size(); i++) {
                    String time = (String) times.get(i);
                    Number tempNum = (Number) temperatures.get(i);
                    Number rainNum = (Number) rains.get(i);

                    double temperature = tempNum.doubleValue();
                    double rain = rainNum.doubleValue();

                    String row = String.format("%-4d | %-20s | %-12.1f | %-10.2f",
                            (i + 1), time, temperature, rain);

                    System.out.println(row);
                    writer.println(row);
                }

                System.out.println("\nТаблица сохранена в файл: " + FORECAST_FILE);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}