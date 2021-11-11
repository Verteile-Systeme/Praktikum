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
}
