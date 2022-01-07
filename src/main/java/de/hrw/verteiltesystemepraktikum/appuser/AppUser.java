package de.hrw.verteiltesystemepraktikum.appuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * The data class represents a simple User with a firstname, lastname, email, address and password which can be stored in a Database.
 * It is called appuser due to the keyword "user" is a reserved keyword in postgres and can not be used as a tablename.
 *
 * @author Dimitrios Barkas
 */
@Entity(
        name = "appuser"
)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
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
