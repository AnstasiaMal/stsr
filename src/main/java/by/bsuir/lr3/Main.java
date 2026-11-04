package by.bsuir.lr3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

/** Точка входа во все четыре части лабораторной работы №3. */
public class Main {
    public static void main(String[] args) throws Exception {
        String city = args.length > 0 ? args[0] : "Минск";
        LocalDate from = args.length > 1 ? LocalDate.parse(args[1]) : LocalDate.parse("2026-10-01");
        LocalDate to = args.length > 2 ? LocalDate.parse(args[2]) : LocalDate.parse("2026-10-03");
        Path output = Path.of("target", "lr3-output");
        Files.createDirectories(output);
        SaxDemo.run(city, from, to);
        DomDemo.run(output);
        JaxbDemo.run(output);
        GsonDemo.run(output);
        System.out.println("Файлы: " + output.toAbsolutePath());
    }
}
