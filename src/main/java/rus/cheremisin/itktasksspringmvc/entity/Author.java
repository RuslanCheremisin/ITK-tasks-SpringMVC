package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    private String alias;

    private int birthYear;

    @OneToMany(mappedBy = "author")
    private Set<Book> writtenBooks = new HashSet<>();

    public Author(String firstName, String lastName, String alias, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.birthYear = birthYear;
    }

    public void setWrittenBooks(Set<Book> writtenBooks) {
        this.writtenBooks.addAll(writtenBooks);
    }
}
