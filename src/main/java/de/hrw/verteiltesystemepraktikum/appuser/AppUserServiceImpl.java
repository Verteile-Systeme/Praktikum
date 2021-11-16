package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser saveUser(@Valid AppUser appUser) {
        if (appUserRepository.existsByEmail(appUser.getEmail())) {
            String errorString = "The provided email " + appUser.getEmail() + " already exists.";
            throw new MailAlreadyExistsException(errorString);
        }
        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        if (!appUserRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new UserNotFoundException(errorString);
        }
        appUserRepository.deleteById(id);
    }

    @Override
    public Long deleteAllUsers() {
        Long entites = appUserRepository.count();
        appUserRepository.deleteAll();
        return entites;
    }

    @Override
    public AppUser updateUserById(AppUser newAppUser, Long id) throws UserNotFoundException {
        if (!appUserRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new UserNotFoundException(errorString);
        }
        return appUserRepository.findById(id)
                .map(appUser -> {
                    appUser.setFirstname(newAppUser.getFirstname());
                    appUser.setLastname(newAppUser.getLastname());
                    appUser.setEmail(newAppUser.getEmail());
                    appUser.setAddress(newAppUser.getAddress());
                    appUser.setPassword(newAppUser.getPassword());
                    return appUserRepository.save(appUser);
                }).orElseGet(() -> {
                    return appUserRepository.save(newAppUser);
                });
    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<AppUser> findUserById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public List<AppUser> updateAllUsers() {
        //TODO: Alle Benutzer updaten
        return null;
    }
}
