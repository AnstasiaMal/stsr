package by.bsuir.lr3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/** Корневой JAXB-объект со списком подразделений. */
@XmlRootElement(name = "organization")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization {
    public String name;
    @XmlElement(name = "department") public List<Department> departments = new ArrayList<>();
    public Organization() { }
    public Organization(String name, List<Department> departments) {
        this.name = name; this.departments = departments;
    }
}
