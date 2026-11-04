package by.bsuir.lr3;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.nio.file.Path;
import java.util.List;

/** Проверяет маршалинг и анмаршалинг организации. */
public class JaxbDemo {
    public static void run(Path output) throws Exception {
        Organization source = new Organization("БГУИР", List.of(
                new Department("A01", "Разработка", "Минск"),
                new Department("B02", "Тестирование", "Брест")));
        var context = JAXBContext.newInstance(Organization.class);
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Path path = output.resolve("organization.xml");
        marshaller.marshal(source, path.toFile());
        Organization restored = (Organization) context.createUnmarshaller().unmarshal(path.toFile());
        System.out.println("JAXB: " + restored.name + ", отделов: " + restored.departments.size());
        restored.departments.forEach(d -> System.out.println("  " + d));
    }
}
