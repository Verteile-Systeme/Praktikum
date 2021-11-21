package de.hrw.verteiltesystemepraktikum.appuser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * The data class represents a simple User which can be stored in a Database.
 * <p>
 * It is called appuser due to the keyword "data" is a reserved keyword in postgres.
 */
@Entity(
        name = "appuser"
)
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{firstname.not.empty}")
    private String firstname;

    @NotEmpty(message = "{lastname.not.empty}")
    private String lastname;

    @NotEmpty(message = "{email.not.empty}")
    @Email
    private String email;

    @NotEmpty(message = "{address.not.empty}")
    private String address;

    @NotEmpty(message = "{password.not.empty}")
    private String password;

    public AppUser(
            String firstname,
            String lastname,
            String email,
            String address,
            String password
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
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
