package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            appUserService.deleteUserById(id);
            return new ResponseEntity<>("Deleted successfully.",HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        long entities = appUserService.deleteAllUsers();
        String response = String.format("%d Entites deleted.", entities);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id,
                                         @Valid @RequestBody AppUser appUser
    ) {
        try {
            return new ResponseEntity<>(appUserService.updateUserById(appUser, id), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAllUsers(@Valid @RequestBody  AppUser updatedAppUser) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(appUserService.updateAllUsers(updatedAppUser));
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addUser(@Valid @RequestBody AppUser appUser) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(appUserService.saveUser(appUser));
        } catch (MailAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @GetMapping
    public List<AppUser> getUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long id) {
        return appUserService.findUserById(id)
                .map(appUser -> new ResponseEntity<>(appUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
