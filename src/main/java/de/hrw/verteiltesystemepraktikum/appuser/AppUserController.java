package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    private final AppUserService appUserService;

    public AppUserController(
            AppUserRepository appUserRepository,
            AppUserService appUserService
    )
    {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody AppUser appUser)
    {
        try {
            return new ResponseEntity(appUserService.saveUser(appUser), HttpStatus.CREATED);
        }   catch (MailAlreadyExistsException ex) {
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


}
