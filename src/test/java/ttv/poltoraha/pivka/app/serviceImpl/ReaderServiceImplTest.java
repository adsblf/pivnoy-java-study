package ttv.poltoraha.pivka.app.serviceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.repository.QuoteRepository;
import ttv.poltoraha.pivka.service.ReaderService;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class ReaderServiceImplTest {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    public void checkCreateQuote() {
        readerService.createQuote("poltoraha_pivka", 4, "Идиот не главный герой, а я.");
        readerService.createQuote("poltoraha_pivka", 2, "Не читал, но друг говорит норм.");
        readerService.createQuote("poltoraha_pivka", 7, "Лучший гайд на планете, советую, " +
                "теги врут.");

        var quotes = quoteRepository.findAll();

        assertEquals(3, quotes.size());
    }
}
