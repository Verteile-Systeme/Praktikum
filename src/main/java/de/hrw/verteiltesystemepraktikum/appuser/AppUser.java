package de.hrw.verteiltesystemepraktikum.appuser;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The data class represents a simple User which can be stored in a Database.
 * <p>
 * It is called appuser due to the keyword "data" is a reserved keyword in postgres.
 */
@Entity(
        name = "appuser"
)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{firstname.not.null}")
    @NotEmpty(message = "{firstname.not.empty}")
    private String firstname;

    @NotNull(message = "{lastname.not.null}")
    @NotEmpty(message = "{lastname.not.empty}")
    private String lastname;

    @NotNull(message = "{email.not.null}")
    @NotEmpty(message = "{email.not.empty}")
    @Email
    private String email;

    @NotNull(message = "{password.not.null}")
    @NotEmpty(message = "{password.not.empty}")
    private String password;

    public AppUser(
            String firstname,
            String lastname,
            String email,
            String password
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }


    public AppUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
