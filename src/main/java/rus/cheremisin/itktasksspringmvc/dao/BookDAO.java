package rus.cheremisin.itktasksspringmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rus.cheremisin.itktasksspringmvc.entity.Book;

import java.util.Optional;

public interface BookDAO extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String title);
}
