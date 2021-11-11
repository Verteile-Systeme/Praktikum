package de.hrw.verteiltesystemepraktikum.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements  AppUserService{

    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        if(appUserRepository.existsByEmail(appUser.getEmail())) {
            String errorString = "The provided email " +  appUser.getEmail() +  " already exists.";
            throw  new MailAlreadyExistsException(errorString);
        }
        AppUser savedAppuser = appUserRepository.save(appUser);
        return savedAppuser;
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        if(!appUserRepository.existsById(id)) {
            String errorString = "The specified id {" +  id +  "} does not exists.";
            throw  new UserNotFoundException(errorString);
        }
        appUserRepository.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        appUserRepository.deleteAll();
    }

    @Override
    public AppUser updateUserById(AppUser newAppUser, Long id) throws UserNotFoundException {
        if(!appUserRepository.existsById(id)) {
            String errorString = "The specified id {" +  id +  "} does not exists.";
            throw  new UserNotFoundException(errorString);
        }
        return appUserRepository.findById(id)
                .map(appUser -> {
                    appUser.setFirstname(newAppUser.getFirstname());
                    appUser.setLastname(newAppUser.getLastname());
                    return appUserRepository.save(appUser);
                }).orElseGet(() -> {
                    return appUserRepository.save(newAppUser);
                });
    }
}
