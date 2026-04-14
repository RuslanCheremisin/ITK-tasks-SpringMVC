package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.DTO.AuthorDTO;
import rus.cheremisin.itktasksspringmvc.dao.AuthorDAO;
import rus.cheremisin.itktasksspringmvc.entity.Author;
import rus.cheremisin.itktasksspringmvc.exceptions.AuthorNotFoundException;
import rus.cheremisin.itktasksspringmvc.mapper.AuthorMapper;
import rus.cheremisin.itktasksspringmvc.service.AuthorService;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO dao;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorDAO dao, AuthorMapper authorMapper) {
        this.dao = dao;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDTO addAuthor(AuthorDTO dto) {
        if (dto == null) {
            throw new NullPointerException("cannot add null author");
        }
        Author newAuthor = authorMapper.toAuthor(dto);
        return authorMapper.toDto(dao.save(newAuthor));
    }

    @Override
    public AuthorDTO getAuthorByFullName(String lastName, String firstName) {
        if (lastName == null || firstName == null) {
            throw new NullPointerException("check lastname and/or firstname, it cannot be null");
        }
        Optional<Author> authorOptional = dao.findAuthorByLastNameAndFirstName(lastName, firstName);
        if (authorOptional.isEmpty()) {
            throw new AuthorNotFoundException("no author by that name is found");
        }
        return authorMapper.toDto(authorOptional.get());
    }
}
