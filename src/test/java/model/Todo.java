package model;

// POJO for /todos responses; Jackson maps JSON fields to record components by name
public record Todo(int userId, int id, String title, boolean completed) {
}
