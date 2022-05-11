package dev.service;

import dev.domain.TaskBase;

import java.util.Comparator;

public class TaskComparator implements Comparator<TaskBase> {

    @Override
    public int compare(TaskBase o1, TaskBase o2) {
        return Integer.compare(o1.getTaskId(), o2.getTaskId());
    }
}
