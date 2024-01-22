package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Keyword;
import nl.itvitae.superrecipe.repo.KeywordRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
public class KeywordController {

    private final KeywordRepo keywordRepo;

    @GetMapping("/")
    public List<Keyword> getAll() {
        return keywordRepo.findAll();
    }
}
