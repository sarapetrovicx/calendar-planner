package rs.raf.projekat1.sara_petrovic_rn4520.model;

import java.time.LocalDateTime;

public class Task {

    LocalDateTime startTime;

    LocalDateTime endTime;

    String title;

    String content;

    Priority priority;

    public Task(LocalDateTime startTime, LocalDateTime endTime, String title, String content, Priority priority) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
        this.priority = priority;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
