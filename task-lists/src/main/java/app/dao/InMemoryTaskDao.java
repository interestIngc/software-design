package app.dao;

import app.model.Task;
import app.model.TaskList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskDao implements TaskDao {
    private final Map<Integer, TaskList> lists = new HashMap<>();
    private final Map<Integer, Map<Integer, Task>> tasks = new HashMap<>();
    private final Map<Integer, Integer>  taskToList = new HashMap<>();
    private int taskListId = 0;
    private int taskId = 0;

    @Override
    public void addList(TaskList list) {
        list.setId(taskListId);
        lists.put(taskListId, list);
        tasks.put(taskListId, new HashMap<>());
        taskListId++;
    }

    @Override
    public void deleteList(int taskListId) {
        lists.remove(taskListId);
        for (Task task : tasks.get(taskListId).values()) {
            taskToList.remove(task.getId());
        }
        tasks.remove(taskListId);
    }

    @Override
    public List<TaskList> getLists() {
        return List.copyOf(lists.values());
    }

    @Override
    public List<Task> getTasksForList(int taskListId) {
        return List.copyOf(tasks.get(taskListId).values());
    }

    @Override
    public void addTaskToList(int taskListId, Task task) {
        task.setId(taskId);
        task.setListId(taskListId);
        tasks.get(taskListId).put(taskId, task);
        taskToList.put(taskId, taskListId);
        taskId++;
    }

    @Override
    public void markTaskCompleted(int taskId) {
        int taskListId = taskToList.get(taskId);
        tasks.get(taskListId).get(taskId).markCompleted();
    }
}
