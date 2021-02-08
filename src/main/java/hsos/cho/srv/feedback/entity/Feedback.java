package hsos.cho.srv.feedback.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @authro Lukas Grewe
 * Feedback Entity
 * Represents a Feedback with an id, a text and an incoming date
 */

@Entity
public class Feedback implements Cloneable{
    //Feedback ID that will be set by EntityManager
    @Id @SequenceGenerator(name = "feedbackSeq", sequenceName = "feedback_id_seq", allocationSize = 1, initialValue = 1) @GeneratedValue(generator = "feedbackSeq")
    private long id;
    //will be set when a Feedback is created
    @Column(nullable = false)
    private String date;
    //incoming text from Feedback
    @Column(nullable = false, length = 512) private String text;
    //used to mark, when its already retrieved
    private boolean wasSeen;

    /**
     * Default Constructor
     */
    public Feedback(){}

    /**
     * Normal Constructor,
     * text incoming, date and wasSeen will be set
     * @param text will be the Feedback test
     */
    public Feedback(String text){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        this.date = LocalDateTime.now().format(formatter);
        this.text = text;
        this.wasSeen = false;
    }

    /**
     * Constructor to clone a Feedback
     * is used by FeedbackRepository
     * @param text -> from origin Feedback
     * @param id -> from origin Feedback
     * @param wasSeen -> from origin Feedback
     * @param d -> from origin Feedback
     */
    private Feedback(String text, long id, boolean wasSeen, String d){
        this.date = d;
        this.text = text;
        this.wasSeen = wasSeen;
        this.id = id;
    }

    /**
     * @author Lukas Grewe
     * @return a deep copy of this Feedback
     */
    @Override
    public Feedback clone(){
        return new Feedback(this.text, this.id, this.wasSeen, this.date);
    }

    /**
     * GETTER AND SETTER BELOW
     */

    public long getId() { return id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public boolean isWasSeen() { return wasSeen; }

    public void setWasSeen(boolean wasSeen) { this.wasSeen = wasSeen; }

    public boolean getWasSeen() { return this.wasSeen; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
