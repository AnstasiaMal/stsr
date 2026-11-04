package by.bsuir.lr3;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Создаёт, читает, ищет и удаляет записи сотрудников средствами DOM. */
public class DomDemo {
    private static DocumentBuilderFactory factory() throws Exception {
        var f = DocumentBuilderFactory.newInstance();
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return f;
    }

    private static void field(Document doc, Element parent, String tag, String value) {
        Element e = doc.createElement(tag);
        e.setTextContent(value);
        parent.appendChild(e);
    }

    private static String value(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }

    private static List<Employee> read(Document doc) {
        List<Employee> result = new ArrayList<>();
        var nodes = doc.getElementsByTagName("employee");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            Element email = (Element) e.getElementsByTagName("email").item(0);
            result.add(new Employee(e.getAttribute("id"), value(e, "name"), value(e, "position"),
                    email.getTextContent(), Boolean.parseBoolean(email.getAttribute("verified")), value(e, "phone")));
        }
        return result;
    }

    private static void save(Document doc, Path path) throws Exception {
        var transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(path.toFile()));
    }

    public static void run(Path output) throws Exception {
        Document doc = factory().newDocumentBuilder().newDocument();
        Element root = doc.createElement("employees");
        doc.appendChild(root);
        for (Employee employee : List.of(
                new Employee("1", "Анна", "Разработчик", "anna@example.com", true, "+375291111111"),
                new Employee("2", "Игорь", "Тестировщик", "igor@example.com", false, "+375292222222"))) {
            Element e = doc.createElement("employee");
            e.setAttribute("id", employee.id());
            field(doc, e, "name", employee.name());
            field(doc, e, "position", employee.position());
            Element email = doc.createElement("email");
            email.setAttribute("verified", Boolean.toString(employee.emailVerified()));
            email.setTextContent(employee.email());
            e.appendChild(email);
            field(doc, e, "phone", employee.phone());
            root.appendChild(e);
        }
        Path path = output.resolve("employees.xml");
        save(doc, path);
        Document loaded = factory().newDocumentBuilder().parse(path.toFile());
        System.out.println("DOM: прочитано сотрудников: " + read(loaded).size());
        System.out.println("Найден id=2: " + read(loaded).stream().filter(e -> e.id().equals("2")).findFirst().orElseThrow());
        var nodes = loaded.getElementsByTagName("employee");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            if (e.getAttribute("id").equals("2")) { e.getParentNode().removeChild(e); break; }
        }
        save(loaded, output.resolve("employees-after-delete.xml"));
        System.out.println("После удаления id=2: " + read(loaded).size());
    }
}
