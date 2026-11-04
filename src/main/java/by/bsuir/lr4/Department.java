package by.bsuir.lr4;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Отдел; удаление отдела каскадно удаляет его сотрудников. */
@Entity @Table(name = "departments")
public class Department {
    @Id @Column(length = 3) private String id;
    @Column(nullable = false, unique = true) private String name;
    @Column(nullable = false) private String location;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Developer> developers = new ArrayList<>();
    protected Department() { }
    public Department(String id, String name, String location) {
        this.id = id; this.name = name; this.location = location;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Developer> getDevelopers() { return developers; }
    @Override public String toString() { return id + " — " + name + " (" + location + ")"; }
}
