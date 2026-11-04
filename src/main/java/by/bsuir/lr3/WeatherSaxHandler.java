package by.bsuir.lr3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/** Собирает измерения из событий SAX. */
public class WeatherSaxHandler extends DefaultHandler {
    private final List<WeatherRecord> records = new ArrayList<>();
    private final StringBuilder text = new StringBuilder();
    private String city;
    private LocalDate date;
    private double temperature;
    private int humidity;

    public List<WeatherRecord> records() { return List.copyOf(records); }

    @Override public void startElement(String uri, String localName, String qName, Attributes attributes) {
        text.setLength(0);
    }

    @Override public void characters(char[] ch, int start, int length) {
        // SAX вправе передать текст одного элемента несколькими вызовами.
        text.append(ch, start, length);
    }

    @Override public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = text.toString().trim();
        try {
            switch (qName) {
                case "city" -> city = value;
                case "date" -> date = LocalDate.parse(value);
                case "temperature" -> temperature = Double.parseDouble(value);
                case "humidity" -> humidity = Integer.parseInt(value);
                case "observation" -> records.add(new WeatherRecord(city, date, temperature, humidity));
                default -> { }
            }
        } catch (RuntimeException e) {
            throw new SAXException("Некорректное значение в теге " + qName + ": " + value, e);
        }
        text.setLength(0);
    }
}
