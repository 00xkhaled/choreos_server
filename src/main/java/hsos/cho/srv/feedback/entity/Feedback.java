package hsos.cho.srv.feedback.entity;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Feedback implements Cloneable{

    @Id
    @SequenceGenerator(name = "feedbackSeq", sequenceName = "feedback_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "feedbackSeq")
    private long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String text;

    private boolean wasSeen;

    public Feedback(){}

    public Feedback(String text){
        this.date = LocalDateTime.now();
        this.text = text;
        this.wasSeen = false;
    }

    private Feedback(String text, long id, boolean wasSeen, LocalDateTime ldt){

        this.date = ldt;
        this.text = text;
        this.wasSeen = wasSeen;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public boolean getWasSeen() {
        return this.wasSeen;
    }

    public LocalDateTime getDateTime() {
        return date;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    public String getDateAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return this.date.format(formatter);
    }

    @Override
    public Feedback clone(){
        return new Feedback(this.text, this.id, this.wasSeen, this.date);
    }
}
