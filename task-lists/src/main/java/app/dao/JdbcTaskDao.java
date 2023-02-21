package app.dao;

import app.model.Task;
import app.model.TaskList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaskDao extends JdbcDaoSupport implements TaskDao {

    public JdbcTaskDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        String createTaskListsQuery = "create table if not exists TaskLists(" +
                "taskListId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name text NOT NULL)";
        String createTasksQuery = "create table if not exists Tasks(" +
                "taskId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "taskListId INTEGER NOT NULL, " +
                "name text NOT NULL, " +
                "date text NOT NULL, " +
                "completed INTEGER NOT NULL, " +
                "foreign key (taskListId) references TaskLists(taskListId)" +
                ")";
        getJdbcTemplate().execute(createTaskListsQuery);
        getJdbcTemplate().execute(createTasksQuery);
    }

    @Override
    public void addList(TaskList list) {
        String sqlQuery = "insert into TaskLists (name) values (?)";
        getJdbcTemplate().update(sqlQuery, list.getName());
    }

    @Override
    public void deleteList(int taskListId) {
        String tasksQuery = "delete from Tasks where taskListId = ?";
        getJdbcTemplate().update(tasksQuery, taskListId);

        String taskListsQuery = "delete from TaskLists where taskListId = ?";
        getJdbcTemplate().update(taskListsQuery, taskListId);
    }

    @Override
    public List<TaskList> getLists() {
        String sqlQuery = "select taskListId, name from TaskLists";
        return getJdbcTemplate().query(sqlQuery, resultSet -> {
            List<TaskList> taskLists = new ArrayList<>();
            while (resultSet.next()) {
                int taskListId = resultSet.getInt("taskListId");
                String name = resultSet.getString("name");
                taskLists.add(new TaskList(taskListId, name));
            }
            return taskLists;
        });
    }

    @Override
    public List<Task> getTasksForList(int taskListId) {
        String sqlQuery = "select * from Tasks where taskListId = " + taskListId;
        return getJdbcTemplate().query(sqlQuery, new ResultSetExtractor<List<Task>>() {
            @Override
            public List<Task> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Task> tasks = new ArrayList<>();
                while (resultSet.next()) {
                    int taskId = resultSet.getInt("taskId");
                    int taskListId = resultSet.getInt("taskListId");
                    String name = resultSet.getString("name");
                    String date = resultSet.getString("date");
                    int completed = resultSet.getInt("completed");
                    Task task = new Task(taskId, taskListId, name, date);
                    if (completed == 1) {
                        task.markCompleted();
                    }
                    tasks.add(task);
                }
                return tasks;
            }
        });
    }

    @Override
    public void addTaskToList(int taskListId, Task task) {
        String sqlQuery = "insert into Tasks (taskListId, name, date, completed) values (?, ?, ?, ?)";
        getJdbcTemplate().update(sqlQuery, taskListId, task.getName(), task.getDate(), 0);
    }

    @Override
    public void markTaskCompleted(int taskId) {
        String sqlQuery = "update Tasks set completed = 1 where taskId = " + taskId;
        getJdbcTemplate().execute(sqlQuery);
    }
}
