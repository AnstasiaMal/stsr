# Лабораторная работа №3 — Marshalling, вариант 8

Автор: Малюкевич А.С. Нужны JDK 21 и Maven 3.9+.

В каталоге репозитория выполните:

```powershell
mvn clean compile exec:java
```

Для своего города и периода (обе даты включены):

```powershell
mvn exec:java '-Dexec.args=Минск 2026-10-01 2026-10-03'
```

SAX читает `src/main/resources/weather.xml`. DOM, JAXB и Gson создают файлы в `target/lr3-output`. Файл `employees-after-delete.xml` показывает результат удаления сотрудника. Описание классов — в `CLASSES.md`; отчёт — в `report-lr3.docx`, его текст и рисунки — в `report-lr3.md` и `report-assets/`.
