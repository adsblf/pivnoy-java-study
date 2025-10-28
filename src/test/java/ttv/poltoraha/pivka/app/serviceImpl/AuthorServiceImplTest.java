package ttv.poltoraha.pivka.app.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dto.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.serviceImpl.AuthorServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ttv.poltoraha.pivka.app.util.TestConst.AUTHORNAME;
import static ttv.poltoraha.pivka.app.util.TestConst.AVG_RATING;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AuthorServiceImplTest {
    @Autowired
    private AuthorServiceImpl authorService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    private AuthorRequestDto authorRequestDto;

    private Integer authorId;
    private Integer fakeAuthorId;
    private Book book;
    private List<Book> bookList;
    private Author author;
    private String tag;
    private Integer authorRatingCount;

    @BeforeEach
    public void setup() {
        authorId = 1;
        fakeAuthorId = 999; // Не существующий id автора
        tag = "Любовь";
        authorRatingCount = 3;
        book = bookRepository.findById(1).get();
        author = authorRepository.findById(authorId).get();
        bookList = List.of(
                bookRepository.findById(1).get(),
                bookRepository.findById(2).get(),
                bookRepository.findById(3).get()
        );
        // Хз, может вытащить инициализацию в другой класс, типа ttv.poltoraha.pivka.app.model
        authorRequestDto = AuthorRequestDto.builder()
                .fullName(AUTHORNAME)
                .avgRating(AVG_RATING)
                .build();
    }

    @Test
    public void testCreate() {
        val beforeCreateAuthorSize = authorRepository.findAll().size();
        authorService.create(authorRequestDto);
        val afterCreateAuthorSize = authorRepository.findAll().size();
        assertNotEquals(beforeCreateAuthorSize, afterCreateAuthorSize);
    }

    @Test
    public void testAddBooks_Success() {
        authorService.addBooks(authorId, bookList);
        assertTrue(this.author.getBooks().containsAll(bookList));
    }

    @Test
    public void testAddBooks_AuthorNotFound() {
        assertThrows(RuntimeException.class, () -> authorService.addBooks(fakeAuthorId, bookList));
    }

    @Test
    public void testAddBook_Success() {
        authorService.addBook(authorId, book);
        assertTrue(author.getBooks().contains(book));
    }

    @Test
    public void testAddBook_AuthorNotFound() {
        assertThrows(RuntimeException.class, () -> authorService.addBook(fakeAuthorId, book));
    }

    @Test
    public void testGetTopAuthorsByTag() {
        List<Author> expectedBooks = List.of(
                authorRepository.findById(1).get(),
                authorRepository.findById(2).get(),
                authorRepository.findById(9).get()
        );
        List<Author> receivedBooks = authorService.getTopAuthorsByTag(tag, authorRatingCount);
        assertEquals(expectedBooks, receivedBooks);
    }

    @Test
    public void testDelete() {
        authorService.delete(authorId);
        assertTrue(authorRepository.findById(authorId).isEmpty());
    }
}
