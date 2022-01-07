package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public AppUser updateUserById(AppUser updatedAppUser, Long id) throws UserNotFoundException {
        if (!appUserRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new UserNotFoundException(errorString);
        }
        return appUserRepository.findById(id)
                .map(appUser -> {
                    appUser.setFirstname(updatedAppUser.getFirstname());
                    appUser.setLastname(updatedAppUser.getLastname());
                    appUser.setEmail(updatedAppUser.getEmail());
                    appUser.setAddress(updatedAppUser.getAddress());
                    appUser.setPassword(updatedAppUser.getPassword());
                    return appUserRepository.save(appUser);
                }).orElseGet(() -> appUserRepository.save(updatedAppUser));
    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<AppUser> findUserById(Long id) {
        if(appUserRepository.findById(id).isEmpty()) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new UserNotFoundException(errorString);
        }
        return appUserRepository.findById(id);
    }

    @Override
    public List<AppUser> updateAllUsers(AppUser updatedAppUser) {
        List<AppUser> updatedUsers = new ArrayList<>();
        List<AppUser> allUsers = this.getAllUsers();
        if (!allUsers.isEmpty()) {
            for (AppUser temp :
                    allUsers) {
                updatedUsers.add(updateUserById(updatedAppUser, temp.getId()));
            }
            return updatedUsers;
        }
        return null;
    }


}
