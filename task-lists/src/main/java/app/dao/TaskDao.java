package app.dao;

import app.model.Task;
import app.model.TaskList;

import java.util.List;

public interface TaskDao {
    void addList(TaskList list);

    void deleteList(int taskListId);

    List<TaskList> getLists();

    List<Task> getTasksForList(int taskListId);

    void addTaskToList(int taskListId, Task task);

    void markTaskCompleted(int taskId);
}
