package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Pageable;
import rus.cheremisin.itktasksspringmvc.DTO.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO addAuthor(AuthorDTO dto);

    AuthorDTO getAuthorByFullName(String lastName, String firstName);

    List<AuthorDTO> getAllAuthors(Pageable pageable);
}
