package todolist.create.list.domain.model;

import java.util.Date;

public class Note {
    private Long id;
    private String content;
    private Date createdAt;
    private Long userId;

    public Note() {}

    public Note(String content, Date createdAt, Long userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}