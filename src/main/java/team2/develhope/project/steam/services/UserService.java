package team2.develhope.project.steam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.repositories.UtenteRepository;

import java.util.List;

@Service
@Component
public class UserService {

    @Autowired
    private UtenteRepository userRepository;

    public void checkUsername(Utente utente) throws Exception {
        if (!userRepository.findUserByUsername(utente.getUsername()).isEmpty()){
            throw new Exception("The user " + utente.getUsername() + " already exist!");
        }
    }
}
