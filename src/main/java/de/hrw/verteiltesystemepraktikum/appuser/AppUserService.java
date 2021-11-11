package de.hrw.verteiltesystemepraktikum.appuser;

public interface AppUserService {

    AppUser saveUser(AppUser appUser) throws MailAlreadyExistsException;

    void deleteUserById(Long id) throws UserNotFoundException;

    void deleteAllUsers();

    AppUser updateUserById(AppUser newAppUser, Long id) throws UserNotFoundException;

}
