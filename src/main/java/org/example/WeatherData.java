package org.example;

public class WeatherData{
    private final double temperature;
    private final double humidity;
    private final double windSpeed;
    private final String forecast;
    public WeatherData(double temperature, double humidity, double windSpeed, String forecast)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.forecast = forecast;
    }
    public double getTemp()
    {
        return Math.floor(temperature);
    }
    public double getHumidity()
    {
        return humidity;
    }
    public double getWindSpeed()
    {
        return windSpeed;
    }
    public double getTemperatureCelsius()
    {
        return Math.floor(temperature - 273.15);
    }
    public double getTemperatureFahrenheit()
    {
        return Math.floor((getTemperatureCelsius() * 2)+ 32);
    }
    public String getForecast()
    {
        return forecast;
    }

}

