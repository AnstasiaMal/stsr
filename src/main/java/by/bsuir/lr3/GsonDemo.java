package by.bsuir.lr3;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.List;

/** Записывает массив занятий в JSON и восстанавливает его. */
public class GsonDemo {
    public static void run(Path output) throws Exception {
        var gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, (com.google.gson.JsonSerializer<LocalTime>)
                        (time, type, context) -> new com.google.gson.JsonPrimitive(time.toString()))
                .registerTypeAdapter(LocalTime.class, (com.google.gson.JsonDeserializer<LocalTime>)
                        (json, type, context) -> LocalTime.parse(json.getAsString()))
                .setPrettyPrinting().create();
        List<Lesson> lessons = List.of(
                new Lesson("Java", LocalTime.of(1, 30), List.of("SAX", "DOM")),
                new Lesson("Базы данных", LocalTime.of(1, 20), List.of("JPA", "Hibernate")));
        Path path = output.resolve("lessons.json");
        Files.writeString(path, gson.toJson(lessons));
        List<Lesson> restored = gson.fromJson(Files.readString(path), new TypeToken<List<Lesson>>() { }.getType());
        System.out.println("Gson: считано занятий: " + restored.size());
        restored.forEach(lesson -> System.out.println("  " + lesson));
    }
}
