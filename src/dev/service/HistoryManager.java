package dev.service;

import dev.domain.TaskBase;

import java.util.List;

/* ТЗ №4: Создайте отдельный интерфейс для управления историей
 просмотров — HistoryManager. У него будет два метода.
 Первый add(Task task) должен помечать задачи как просмотренные,
 а второй getHistory() — возвращать их список. */
public interface HistoryManager {
    void add(TaskBase task);

    List<TaskBase> getHistory();
}
