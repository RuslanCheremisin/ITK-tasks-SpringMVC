package rus.cheremisin.itktasksspringmvc.DTO;

import rus.cheremisin.itktasksspringmvc.enums.Genre;

import java.util.List;

public record BookDTO(
        String title,
        AuthorDTO author,
        int publishingYear,
        List<Genre> genres,
        String description) {
}
