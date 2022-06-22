package dev.service;

import dev.domain.TaskBase;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    final TaskBase task;

    public ManagerSaveException(final String message) {
        super(message);
        this.task = null;
    }

    public ManagerSaveException(final String message, TaskBase task) {
        super(message);
        this.task = task;
    }

    public TaskBase getTask(){
        return task;
    }
}
