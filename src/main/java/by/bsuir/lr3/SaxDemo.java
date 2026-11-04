package by.bsuir.lr3;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;

/** Вычисляет среднюю температуру по городу и включительному периоду. */
public class SaxDemo {
    public static void run(String city, LocalDate from, LocalDate to) throws Exception {
        if (from.isAfter(to)) throw new IllegalArgumentException("Начало периода позже конца");
        var factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        var handler = new WeatherSaxHandler();
        try (InputStream input = SaxDemo.class.getResourceAsStream("/weather.xml")) {
            if (input == null) throw new IllegalStateException("Не найден weather.xml");
            factory.newSAXParser().parse(input, handler);
        }
        List<WeatherRecord> selected = handler.records().stream()
                .filter(r -> r.city().equalsIgnoreCase(city) && !r.date().isBefore(from) && !r.date().isAfter(to))
                .toList();
        System.out.printf("SAX: %s, %s — %s; измерений: %d%n", city, from, to, selected.size());
        if (selected.isEmpty()) System.out.println("Данных за период нет");
        else System.out.printf("Средняя температура: %.2f °C%n", selected.stream().mapToDouble(WeatherRecord::temperature).average().orElseThrow());
    }
}
