package rus.cheremisin.itktasksspringmvc.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rus.cheremisin.itktasksspringmvc.DTO.BookDTO;
import rus.cheremisin.itktasksspringmvc.service.BookService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<BookDTO> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/byTitle/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@Valid @PathVariable("title") String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping()
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDto) {
        return ResponseEntity.ok(bookService.addBook(bookDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> editBook(@PathVariable("id") @Min(1) Long id,
                                            @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.editBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") @Min(1) Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}