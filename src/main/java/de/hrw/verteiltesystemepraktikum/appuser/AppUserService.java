package de.hrw.verteiltesystemepraktikum.appuser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser saveUser(AppUser appUser) throws MailAlreadyExistsException;

    void deleteUserById(Long id) throws UserNotFoundException;

    void deleteAllUsers();

    AppUser updateUserById(AppUser newAppUser, Long id) throws UserNotFoundException;

    List<AppUser> getAllUsers();

    Optional<AppUser> findUserById(Long id);



}
