package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherUI extends Application{
    private TextField textField;
    private Label weatherLabel;
    private static final String API_KEY="f5c63fdf53067aee3fa13c7e1efc0b7f";
    private static final String API_URL="http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=%s";

    @Override
    public void start(Stage primaryStage) {
        // Launch the WeatherUI
         initialiseUI(primaryStage);
    }
    public void initialiseUI(Stage primaryStage) {
        // Create UI components
        weatherLabel = new Label("Weather information will be displayed here");
        textField = new TextField();

        // Create a button to submit the text
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            // Retrieve the text from the text field when the button is clicked
            String enteredCity = textField.getText();
            // Fetch weather data and update UI
            fetchWeatherDataAndUpdateUI(enteredCity);
        });

        // Create a layout and add components
        VBox root = new VBox(10); // Vertical layout with spacing 10
        root.getChildren().addAll(textField, submitButton, weatherLabel);

        // Create the scene
        Scene scene = new Scene(root, 300, 200);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle("Weather Application");

        // Show the stage
        primaryStage.show();
    }

    // Method to fetch weather data and update UI
    private void fetchWeatherDataAndUpdateUI(String city) {

        try {
            // Make HTTP request to fetch weather data
            String apiUrl = String.format(API_URL, city, API_KEY);
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            // Read and parse JSON response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extract weather information
                double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
                double humidity = jsonResponse.getJSONObject("main").getDouble("humidity");
                double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
                String forecast = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("main");

                WeatherData weatherData = new WeatherData(temperature, humidity, windSpeed, forecast);
                // Update UI with weather data
                String weatherDataResult = "City: " + city + "\n" +
                        "Temperature (K): " + weatherData.getTemp() + " K\n" +
                        "Temperature (C): " + weatherData.getTemperatureCelsius() + "C\n"+
                        "Temperature (F): " + weatherData.getTemperatureFahrenheit() + "F\n"+
                        "Humidity: " + weatherData.getHumidity() + "%\n" +
                        "Wind Speed: " + weatherData.getWindSpeed() + " m/s\n" +
                        "Forecast: " + weatherData.getForecast();
                weatherLabel.setText(weatherDataResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


