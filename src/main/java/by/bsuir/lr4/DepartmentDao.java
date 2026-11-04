package by.bsuir.lr4;

import java.util.List;

/** Операции с отделами, включая задания поиска, добавления и удаления. */
public class DepartmentDao {
    public Department findDepartmentById(String id) {
        return HibernateUtil.tx(s -> s.find(Department.class, id));
    }
    public Department addDepartment(Department department) {
        return HibernateUtil.tx(s -> { s.persist(department); return department; });
    }
    public void deleteDepartment(String id) {
        HibernateUtil.tx(s -> {
            Department department = s.find(Department.class, id);
            if (department == null) throw new IllegalArgumentException("Отдел не найден: " + id);
            s.remove(department);
            return null;
        });
    }
    public List<Department> getDepartmentsWithDevelopers() {
        // JOIN FETCH выбирает отделы и сотрудников одним запросом.
        return HibernateUtil.tx(s -> s.createQuery(
                "select distinct d from Department d left join fetch d.developers order by d.id", Department.class)
                .getResultList());
    }
    public List<Department> findByLocation(String location) {
        return HibernateUtil.tx(s -> s.createQuery(
                "from Department d where d.location = :location order by d.id", Department.class)
                .setParameter("location", location).getResultList());
    }
}
