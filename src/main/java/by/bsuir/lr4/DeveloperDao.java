package by.bsuir.lr4;

import java.util.List;

/** Создание и перевод сотрудников; два дополнительных SELECT-запроса. */
public class DeveloperDao {
    public Developer addDeveloper(String name, String specialty, int experience, String departmentId) {
        return HibernateUtil.tx(s -> {
            Department department = s.find(Department.class, departmentId);
            if (department == null) throw new IllegalArgumentException("Отдел не найден: " + departmentId);
            Developer developer = new Developer(name, specialty, experience, department);
            s.persist(developer);
            return developer;
        });
    }
    public Developer updateDevelopersDepartment(int developerId, String departmentId) {
        return HibernateUtil.tx(s -> {
            Developer developer = s.find(Developer.class, developerId);
            Department department = s.find(Department.class, departmentId);
            if (developer == null || department == null) throw new IllegalArgumentException("Сотрудник или отдел не найден");
            developer.setDepartment(department); // managed entity обновится при commit
            return developer;
        });
    }
    public Developer findById(int id) { return HibernateUtil.tx(s -> s.createQuery(
            "select d from Developer d join fetch d.department where d.id = :id", Developer.class)
            .setParameter("id", id).uniqueResult()); }
    public void deleteDeveloper(int id) {
        HibernateUtil.tx(s -> {
            Developer developer = s.find(Developer.class, id);
            if (developer != null) s.remove(developer);
            return null;
        });
    }
    public List<Developer> findByMinExperience(int years) {
        return HibernateUtil.tx(s -> s.createQuery(
                "from Developer d where d.experience >= :years order by d.experience desc", Developer.class)
                .setParameter("years", years).getResultList());
    }
    public List<Developer> findByDepartment(String departmentId) {
        return HibernateUtil.tx(s -> s.createQuery(
                "from Developer d where d.department.id = :id order by d.name", Developer.class)
                .setParameter("id", departmentId).getResultList());
    }
}
