package dev.service;

import dev.domain.Epic;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTasksManagerTest extends TaskManagerTestAbsctract<InMemoryTasksManager> {

    @Override
    @BeforeEach
    void beforeEach() {
        manager = Managers.SetMemoryTasksManager();
        manager.removeAllTasks();
        manager.createTask("Первая задача!");
        Epic epic = manager.createEpic("Эпик-задача");
        manager.createSubtask(epic.getTaskId(), "Подзадача 1");
        manager.createSubtask(epic.getTaskId(), "Подзадача 2");
    }
}