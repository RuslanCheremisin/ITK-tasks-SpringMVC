package rus.cheremisin.itktasksspringmvc.service;

import rus.cheremisin.itktasksspringmvc.DTO.AuthorDTO;

public interface AuthorService {
    AuthorDTO addAuthor(AuthorDTO dto);

    AuthorDTO getAuthorByFullName(String lastName, String firstName);
}
