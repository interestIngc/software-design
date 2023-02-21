package app.model;

public class TaskList {
    private int id;
    private String name;

    public TaskList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TaskList() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
