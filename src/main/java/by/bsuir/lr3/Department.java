package by.bsuir.lr3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/** Подразделение организации для JAXB. */
@XmlAccessorType(XmlAccessType.FIELD)
public class Department {
    @XmlAttribute public String id;
    public String name;
    public String location;
    public Department() { }
    public Department(String id, String name, String location) {
        this.id = id; this.name = name; this.location = location;
    }
    @Override public String toString() { return id + " — " + name + " (" + location + ")"; }
}
