package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
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
    ) {
        try {
            return new ResponseEntity(appUserService.updateUserById(appUser, id), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity addUser(@Valid @RequestBody AppUser appUser) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(appUserService.saveUser(appUser));
        } catch (MailAlreadyExistsException | ConstraintViolationException ex) {
            if (ex.getClass().equals(MailAlreadyExistsException.class)) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                Map<String, String> constraintViolations = new HashMap<>();
                ((ConstraintViolationException) ex).getConstraintViolations().forEach((constraintViolation -> {
                    constraintViolations.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
                }));
                return new ResponseEntity(constraintViolations, HttpStatus.BAD_REQUEST);
            }

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

}
