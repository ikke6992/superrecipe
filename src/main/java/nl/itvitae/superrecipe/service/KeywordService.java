package nl.itvitae.superrecipe.service;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Keyword;
import nl.itvitae.superrecipe.repo.KeywordRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepo keywordRepo;

    public Keyword getOrCreate(String value) {
        return keywordRepo.findByValue().orElseGet(() -> keywordRepo.save(new Keyword(value)));
    }
}
