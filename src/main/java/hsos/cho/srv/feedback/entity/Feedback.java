package hsos.cho.srv.feedback.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Feedback {

    @Id
    @SequenceGenerator(name = "feedbackSeq", sequenceName = "feedback_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "feedbackSeq")
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String text;

    public Feedback(){}

    public Feedback(String text){
        this.date = LocalDate.now();
        this.text = text;
    }

    public long getId() {
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
