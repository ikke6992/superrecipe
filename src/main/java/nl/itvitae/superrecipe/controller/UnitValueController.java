package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.UnitValue;
import nl.itvitae.superrecipe.repo.UnitValueRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/units")
public class UnitValueController {
    private final UnitValueRepo unitValueRepo;

    @GetMapping("/")
    public List<UnitValue> getAll() {
        return unitValueRepo.findAll();
    }
}
