package by.bsuir.lr3;

import java.time.LocalDate;

/** Одно измерение погоды из XML. */
public record WeatherRecord(String city, LocalDate date, double temperature, int humidity) { }
