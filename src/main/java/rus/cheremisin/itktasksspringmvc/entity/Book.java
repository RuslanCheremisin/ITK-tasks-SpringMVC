package rus.cheremisin.itktasksspringmvc.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import rus.cheremisin.itktasksspringmvc.enums.Genre;

import java.util.List;

@RequiredArgsConstructor
@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @ManyToOne(targetEntity = Author.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Author author;

    private int publishingYear;

    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

    private String description;



    public Book(String title, Author author, int publishingYear, List<Genre> genres, String description) {
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.genres = genres;
        this.description = description;
    }

}
