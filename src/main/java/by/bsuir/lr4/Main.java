package by.bsuir.lr4;

import java.nio.file.Files;
import java.nio.file.Path;

/** Воспроизводимый сценарий для проверки CRUD, связей и трёх SELECT. */
public class Main {
    public static void main(String[] args) throws Exception {
        Files.createDirectories(Path.of("target"));
        var departments = new DepartmentDao();
        var developers = new DeveloperDao();
        var users = new UserDao();
        try {
            // Повторный запуск использует ту же локальную БД и не дублирует исходные записи.
            if (departments.findDepartmentById("A01") == null)
                departments.addDepartment(new Department("A01", "Разработка", "Минск"));
            if (departments.findDepartmentById("B02") == null)
                departments.addDepartment(new Department("B02", "Тестирование", "Брест"));
            if (developers.findByDepartment("A01").isEmpty()) {
                Developer anna = developers.addDeveloper("Анна", "Java", 5, "A01");
                users.addUser(anna.getId(), "anna", "demo", UserRole.ROLE_ADMIN);
            }
            System.out.println("Поиск отдела A01: " + departments.findDepartmentById("A01"));
            System.out.println("Поиск пользователя anna: " + users.getUserByUsername("anna"));

            Developer temporary = developers.addDeveloper("Игорь", "QA", 2, "A01");
            developers.updateDevelopersDepartment(temporary.getId(), "B02");
            System.out.println("Перевод сотрудника: " + developers.findById(temporary.getId()).getDepartment());

            System.out.println("SELECT 1 — отделы Минска: " + departments.findByLocation("Минск"));
            System.out.println("SELECT 2 — опыт от 3 лет: " + developers.findByMinExperience(3));
            System.out.println("SELECT 3 — сотрудники B02: " + developers.findByDepartment("B02"));
            System.out.println("Отделы с сотрудниками: ");
            for (Department department : departments.getDepartmentsWithDevelopers())
                System.out.println("  " + department + ": " + department.getDevelopers());

            // Демонстрируем каскадное удаление на временном отделе.
            departments.addDepartment(new Department("T03", "Временный", "Гомель"));
            Developer deletedWithDepartment = developers.addDeveloper("Олег", "Стажёр", 0, "T03");
            users.addUser(deletedWithDepartment.getId(), "temporary", "demo", UserRole.ROLE_USER);
            departments.deleteDepartment("T03");
            System.out.println("Каскадное удаление: отдел=" + departments.findDepartmentById("T03")
                    + ", сотрудник=" + developers.findById(deletedWithDepartment.getId())
                    + ", аккаунт=" + users.getUserByUsername("temporary"));
            developers.deleteDeveloper(temporary.getId());
            System.out.println("База данных: target/lr4-db.mv.db");
        } finally {
            HibernateUtil.close();
        }
    }
}
