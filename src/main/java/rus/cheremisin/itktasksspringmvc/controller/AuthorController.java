package rus.cheremisin.itktasksspringmvc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rus.cheremisin.itktasksspringmvc.DTO.AuthorDTO;
import rus.cheremisin.itktasksspringmvc.service.AuthorService;

@RestController
@Validated
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@Valid @RequestBody AuthorDTO dto) {
        return ResponseEntity.ok(authorService.addAuthor(dto));
    }

    @GetMapping("/{lastName}+{firstName}")
    public ResponseEntity<AuthorDTO> getAuthorByFullName(@Valid @PathVariable("lastName") String lastName,
                                                         @Valid @PathVariable("firstName") String firstName) {
        return ResponseEntity.ok(authorService.getAuthorByFullName(lastName, firstName));
    }


}
