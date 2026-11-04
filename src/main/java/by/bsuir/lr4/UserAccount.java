package by.bsuir.lr4;

import jakarta.persistence.*;

/** Учётная запись сотрудника с тем же первичным ключом. */
@Entity @Table(name = "users")
public class UserAccount {
    @Id private Integer id;
    @OneToOne(fetch = FetchType.LAZY) @MapsId @JoinColumn(name = "id")
    private Developer developer;
    @Column(nullable = false, unique = true) private String username;
    @Column(nullable = false) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private UserRole role;
    protected UserAccount() { }
    public UserAccount(Developer developer, String username, String password, UserRole role) {
        this.developer = developer; this.username = username; this.password = password; this.role = role;
        developer.setAccount(this);
    }
    public String getUsername() { return username; }
    public UserRole getRole() { return role; }
    public Developer getDeveloper() { return developer; }
    @Override public String toString() { return username + " (" + role + ", " + developer.getName() + ")"; }
}
