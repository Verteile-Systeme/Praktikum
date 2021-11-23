package de.hrw.verteiltesystemepraktikum.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The data class represents a contanct request with an author, email, subject, content and whether it is read and answered.
 *
 * @author Dimitrios Barkas
 */
@Entity(
        name = "question"
)
@NoArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String author;

    @NotEmpty
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;

    @JsonIgnore
    private boolean read;

    @JsonIgnore
    private boolean answered;

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Question)) {
            return false;
        }
        Question other = (Question) o;
        boolean authorEquals = (this.author == null && other.author == null) ||
                (this.author != null && this.author.equals(other.author));
        boolean emailEquals = (this.email == null && other.email == null) ||
                (this.email != null && this.email.equals(other.email));
        boolean subjectEquals = (this.subject == null && other.subject == null) ||
                (this.subject != null && this.subject.equals(other.subject));
        boolean contentEquals = (this.content == null && other.content == null) ||
                (this.content != null && this.content.equals(other.content));
        return authorEquals && emailEquals && subjectEquals && contentEquals;
    }

}
