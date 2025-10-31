package ttv.poltoraha.pivka.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dto.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Контроллеры - это классы для создания внешних http ручек. Чтобы к нам могли прийти по http, например, через постман
// Или если у приложухи есть веб-морда, каждое действие пользователя - это http запросы
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final CustomMetrics customMetrics;

    @Timed(value="author.create.time", description="Время создания автора")
    @PostMapping("/create")
    public void createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        authorService.create(authorRequestDto);
    }

    @Timed(value="author.delete.time", description="Время удаления автора")
    @PostMapping("/delete")
    public void deleteAuthorById(@RequestParam Integer id) {
        authorService.delete(id);
    }

    @Timed(value="author.add.books.time", description="Время добавления кни")
    @PostMapping("/add/books")
    public void addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        authorService.addBooks(id, books);
    }
}
