package dev.service;

import dev.domain.TaskBase;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int RECORD_COUNT = 10;
    private List<TaskBase> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    /* ТЗ №4: От повторных просмотров избавляться не нужно. */
    @Override
    public void add(TaskBase task) {
        history.add(task);
        trimHistory();
    }

    @Override
    public List<TaskBase> getHistory() {
        return history;
    }

    private void trimHistory() {
        if (history.size() > RECORD_COUNT) {
            history = history.subList(history.size() - RECORD_COUNT, history.size());
        }
    }
}
