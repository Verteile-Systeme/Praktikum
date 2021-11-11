package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    private final AppUserService appUserService;

    public AppUserController(
            AppUserRepository appUserRepository,
            AppUserService appUserService
    ) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {
        try {
            appUserService.deleteUserById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteAllUsers() {
        appUserService.deleteAllUsers();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserById(@PathVariable Long id,
                                         @Valid @RequestBody AppUser appUser
    ){
        try{
            return new ResponseEntity(appUserService.updateUserById(appUser, id), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody AppUser appUser) {
        try {
            return new ResponseEntity(appUserService.saveUser(appUser), HttpStatus.CREATED);
        } catch (MailAlreadyExistsException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long id) {
        return appUserRepository.findById(id)
                .map(appUser -> new ResponseEntity<>(appUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errors;
    }
}
