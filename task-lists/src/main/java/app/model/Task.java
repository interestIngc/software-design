package app.model;

public class Task {
    private int id;
    private int listId;
    private String name;
    private String date;
    private boolean completed = false;

    public Task(int id, int listId, String name, String date) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.date = date;
    }

    public Task() {
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void markCompleted() {
        completed = true;
    }
}
