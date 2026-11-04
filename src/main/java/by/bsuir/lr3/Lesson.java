package by.bsuir.lr3;

import java.time.LocalTime;
import java.util.List;

/** Занятие, сохраняемое в JSON. */
public record Lesson(String subject, LocalTime duration, List<String> topics) { }
