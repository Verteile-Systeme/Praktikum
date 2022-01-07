package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        appUserService.deleteUserById(id);
        return "Deleted successfully";
    }

    @DeleteMapping
    public String deleteAllUsers() {
        return String.format("%d Entities deleted", appUserService.deleteAllUsers());
    }

    @PutMapping("/{id}")
    public String updateUserById(@PathVariable Long id,
                                            @Valid @RequestBody AppUser appUser
    ){
        appUserService.updateUserById(appUser, id);
        return "Updated successfully";
    }

    @PutMapping
    public String updateAllUsers(@Valid @RequestBody AppUser updatedAppUser) {
        appUserService.updateAllUsers(updatedAppUser);
        return "Updated successfully";
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addUser(@Valid @RequestBody AppUser appUser) {
        appUserService.saveUser(appUser);
        return "Created successfully";
    }

    @GetMapping
    public List<AppUser> getUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<AppUser> getUser(@PathVariable Long id) {
        return appUserService.findUserById(id);
    }

    @ExceptionHandler({MailAlreadyExistsException.class})
    public ResponseEntity<String> handleException(MailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }


}
