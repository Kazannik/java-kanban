package dev.service;

import dev.domain.TaskBase;

import java.util.List;

/* ТЗ №4: Создайте отдельный интерфейс для управления историей
 просмотров — HistoryManager. У него будет два метода.
 Первый add(Task task) должен помечать задачи как просмотренные,
 а второй getHistory() — возвращать их список.

   ТЗ №5: Интерфейс HistoryManager будет иметь следующую структуру:
      "public interface HistoryManager {
            void add(Task task);
            void remove(int id);
            List<Task> getHistory();
       } " */
public interface HistoryManager {
    void add(TaskBase task);

    void remove(int id);

    TaskBase getFirst();

    TaskBase getLast();

    List<TaskBase> getHistory();

    List<Integer> getHistoryId();

    void clear();
}
