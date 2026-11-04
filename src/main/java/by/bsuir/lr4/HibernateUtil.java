package by.bsuir.lr4;

import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Общая фабрика Hibernate и безопасная граница транзакции. */
public final class HibernateUtil {
    private static final SessionFactory FACTORY = new Configuration()
            .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
            .setProperty("hibernate.connection.url", "jdbc:h2:file:./target/lr4-db;DB_CLOSE_ON_EXIT=FALSE")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .setProperty("hibernate.show_sql", "false")
            .addAnnotatedClass(Department.class)
            .addAnnotatedClass(Developer.class)
            .addAnnotatedClass(UserAccount.class)
            .buildSessionFactory();
    private HibernateUtil() { }
    public static <T> T tx(Function<Session, T> operation) {
        try (Session session = FACTORY.openSession()) {
            var transaction = session.beginTransaction();
            try {
                T result = operation.apply(session);
                transaction.commit();
                return result;
            } catch (RuntimeException ex) {
                transaction.rollback();
                throw ex;
            }
        }
    }
    public static void close() { FACTORY.close(); }
}
