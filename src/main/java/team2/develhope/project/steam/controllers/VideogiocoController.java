package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videogioco")
public class VideogiocoController {
    @Autowired
    private VideogiocoRepository videogiocoRepository;



    @GetMapping("/esplora")
    public Page<Videogioco> get(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size) {
        if (page.isPresent() && size.isPresent()) {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "titolo"));
            Pageable pageable = PageRequest.of(page.get(), size.get(), sort);
            Page<Videogioco> videogiochi = videogiocoRepository.findAll(pageable);
            return videogiochi;
        } else {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "titolo"));
            Pageable pageable = PageRequest.of(0,videogiocoRepository.findAll().size(), sort);
            Page<Videogioco> videogiochi = videogiocoRepository.findAll(pageable);
            return videogiochi;
        }
    }
    @GetMapping("trovauno")
      public List<Videogioco> trovaUnVideogioco(@RequestParam String titolo){

        return videogiocoRepository.findAllVideogiocoLikeTitolo(titolo);
    }
    @PostMapping("/inserisci")
    public Videogioco inserisciVideogioco(@RequestBody Videogioco videogioco) {
        return videogiocoRepository.save(videogioco);
    }
}
