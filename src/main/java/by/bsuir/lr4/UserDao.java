package by.bsuir.lr4;

/** Поиск учётной записи по имени, как требует методичка. */
public class UserDao {
    public UserAccount addUser(int developerId, String username, String password, UserRole role) {
        return HibernateUtil.tx(s -> {
            Developer developer = s.find(Developer.class, developerId);
            if (developer == null) throw new IllegalArgumentException("Сотрудник не найден: " + developerId);
            UserAccount user = new UserAccount(developer, username, password, role);
            s.persist(user);
            return user;
        });
    }
    public UserAccount getUserByUsername(String username) {
        return HibernateUtil.tx(s -> s.createQuery(
                "select u from UserAccount u join fetch u.developer where u.username = :name", UserAccount.class)
                .setParameter("name", username).uniqueResult());
    }
}
