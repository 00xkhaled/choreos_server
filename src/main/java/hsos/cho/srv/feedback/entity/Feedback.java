package hsos.cho.srv.feedback.entity;

import java.time.LocalDate;

public class Feedback {
    private String id;
    private LocalDate date;
    private String text;

    public Feedback(){}

    public Feedback(String id, LocalDate date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
