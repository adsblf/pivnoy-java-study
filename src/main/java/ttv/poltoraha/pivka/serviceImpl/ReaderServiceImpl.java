package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.entity.Reading;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.repository.QuoteRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.service.ReaderService;
import util.MyUtility;

@Service
@RequiredArgsConstructor
@Transactional
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final QuoteRepository quoteRepository;
    @Override
    public void createQuote(String username, Integer book_id, String text) {
        val newQuote = new Quote();
        val reader = readerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Entity reader with id = " + username + " was not found"));
        val book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Entity book with id = " + book_id + " was not found"));
        newQuote.setBook(book);
        newQuote.setText(text);
        newQuote.setReader(reader);

//        reader.getQuotes().add(newQuote);
//        readerRepository.save(reader);

        quoteRepository.save(newQuote);

        /*
        Как я понял идея в следующем:
            1. До создания класса quoteRepository, мы брали читателя, получали у него список цитат, добавляли нашу цитату в этот список,
            после чего обновляли пользователя. При этом саму цитату никуда не сохраняли.
            2. После добавления класса quoteRepository, мы сохраняем цитату в отдельной таблице, а пользователя не трогаем.

        Нахуя делать так, а не оставить как было? Я нашел три причины:
            1. Это быстрее.
            Потому что нам не надо получать список цитат пользователя, добавлять в него новую цитату, обновлять пользователя и делать патч-запрос на сервер.
            С quoteRepository, мы получили нужные данные и сделали пост-запрос в отдельную таблицу, всё.

            2. Мы исключаем возможность изменения данных о пользователе в таблице, так как отправляем к этой таблице только GET-запрос.
            Я хуй знает можно ли было сделать SQL-инъекцию, потому что hibernate, вроде как, защищает нас от этого. Но если бы это было возможно,
            всей бд пришла бы жопа.

            3. Не помню, но вроде это неправильная архитектура БД. Что-то на подобии нарушения атомарности, не помню, короче неправильно
            хранить массив данных в таблице, если этот массив можно представить отдельной таблицей и связать ее с основной через FK.
         */
    }

    @Override
    public void addFinishedBook(String username, Integer bookId) {
        val reader = MyUtility.findEntityById(readerRepository.findByUsername(username), "reader", username);

        val book = MyUtility.findEntityById(bookRepository.findById(bookId), "book", bookId.toString());

        val reading = new Reading();
        reading.setReader(reader);
        reading.setBook(book);

        reader.getReadings().add(reading);

        readerRepository.save(reader);
    }

    @Override
    public void createReader(String username, String password) {
        val reader = new Reader();
        reader.setUsername(username);
        reader.setPassword(password);

        readerRepository.save(reader);
    }
}
