package app.controller;

import app.dao.TaskDao;
import app.model.Task;
import app.model.TaskList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping(value = "/task-lists/preview")
    public String getTaskLists(ModelMap modelMap) {
        List<TaskList> taskLists = taskDao.getLists();
        prepareMainModelMap(modelMap, taskLists);
        return "index";
    }

    @PostMapping(value = "/task-lists/add")
    public String addTaskList(@ModelAttribute TaskList taskList) {
        taskDao.addList(taskList);
        return "redirect:/task-lists/preview";
    }

    @PostMapping(value = "/task-lists/delete")
    public String deleteTaskList(@RequestParam int taskListId) {
        taskDao.deleteList(taskListId);
        return "redirect:/task-lists/preview";
    }

    @PostMapping(value = "/tasks/{taskListId}/add")
    public String addTaskToList(
            @PathVariable("taskListId") int taskListId,
            @ModelAttribute Task task) {
        taskDao.addTaskToList(taskListId, task);
        return "redirect:/tasks/" + taskListId;
    }

    @PostMapping(value = "tasks/{taskListId}/complete")
    public String markTaskCompleted(
            @PathVariable("taskListId") int taskListId,
            @RequestParam int taskId) {
        taskDao.markTaskCompleted(taskId);
        return "redirect:/tasks/" + taskListId;
    }

    @GetMapping(value = "/tasks/{taskListId}")
    public String getTasksForList(
            @PathVariable("taskListId") int taskListId,
            ModelMap modelMap) {
        List<Task> tasks = taskDao.getTasksForList(taskListId);
        prepareTaskListDisplayMap(modelMap, taskListId, tasks);
        return "taskDisplayView";
    }

    private void prepareMainModelMap(ModelMap modelMap, List<TaskList> taskLists) {
        modelMap.addAttribute("taskLists", taskLists);
        modelMap.addAttribute("taskList", new TaskList());
    }

    private void prepareTaskListDisplayMap(ModelMap modelMap, int taskListId, List<Task> tasks) {
        modelMap.addAttribute("taskListId", taskListId);
        modelMap.addAttribute("tasks", tasks);
        modelMap.addAttribute("task", new Task());
    }
}
