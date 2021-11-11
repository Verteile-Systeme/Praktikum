package de.hrw.verteiltesystemepraktikum.appuser;

public interface AppUserService {

    AppUser saveUser(AppUser appUser) throws MailAlreadyExistsException;

}
