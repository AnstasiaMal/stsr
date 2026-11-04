package by.bsuir.lr3;

/** Модель сотрудника для DOM: два дополнительных поля и атрибут verified. */
public record Employee(String id, String name, String position, String email, boolean emailVerified, String phone) { }
