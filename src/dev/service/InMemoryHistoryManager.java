package dev.service;

import dev.domain.TaskBase;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<TaskBase> history;

    public InMemoryHistoryManager() {
        history = new CustomLinkedList<>();
    }

    /* ТЗ №4: От повторных просмотров избавляться не нужно. */
    @Override
    public void add(TaskBase task) {
        history.add(task);
    }

    /* ТЗ №5. */
    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public TaskBase getFirst() {
        return history.getFirst();
    }

    @Override
    public TaskBase getLast() {
        return history.getLast();
    }

    /* ТЗ №5: Реализация метода getHistory должна перекладывать задачи из связного списка
     в ArrayList для формирования ответа. */
    @Override
    public List<TaskBase> getHistory() {
        return history.getTasks();
    }

    @Override
    public List<Integer> getHistoryId() {
        return history.getTasksId();
    }

    @Override
    public void clear() {
        history.clear();
    }
}
