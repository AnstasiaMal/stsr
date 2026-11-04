package by.bsuir.lr4;

import jakarta.persistence.*;

/** Сотрудник, связанный с отделом и, при наличии, учётной записью. */
@Entity @Table(name = "developers")
public class Developer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String specialty;
    private int experience;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @OneToOne(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAccount account;
    protected Developer() { }
    public Developer(String name, String specialty, int experience, Department department) {
        this.name = name; this.specialty = specialty; this.experience = experience; this.department = department;
    }
    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getExperience() { return experience; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public void setAccount(UserAccount account) { this.account = account; }
    @Override public String toString() { return id + " — " + name + ", " + specialty + ", опыт " + experience; }
}
