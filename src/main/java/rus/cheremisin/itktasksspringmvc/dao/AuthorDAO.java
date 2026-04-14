package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rus.cheremisin.itktasksspringmvc.entity.Author;

import java.util.Optional;

public interface AuthorDAO extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByLastNameAndFirstName(String lastName, String firstName);
}
