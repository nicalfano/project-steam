package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

@RestController
@RequestMapping("/videogioco")
public class VideogiocoController {
    @Autowired
    private VideogiocoRepository videogiocoRepository;
    @PostMapping
    public Videogioco inserisciVideogioco (@RequestBody Videogioco videogioco){
        return videogiocoRepository.save(videogioco);
    }
}
