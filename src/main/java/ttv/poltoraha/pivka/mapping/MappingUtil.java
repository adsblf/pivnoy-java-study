package ttv.poltoraha.pivka.mapping;

import org.springframework.stereotype.Component;
import ttv.poltoraha.pivka.dto.request.AuthorRequestDto;
import ttv.poltoraha.pivka.dto.request.ReviewRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Review;

// Маппер - класс для того, чтобы превращать наши дто в Entity и наоборот
@Component
public class MappingUtil {
    public static Review fromRequestDto(ReviewRequestDto dto, Book book) {
        return Review.builder()
                .book(book)
                .text(dto.getText())
                .readerUsername(dto.getReaderUsername())
                .rating(dto.getRating())
                .build();
    }

    public static Author fromRequestDto(AuthorRequestDto dto) {
        return Author.builder()
                .fullName(dto.getFullName())
                .avgRating(dto.getAvgRating())
                .build();
    }

    public static Review updateFromRequestDto(Review review, ReviewRequestDto dto) {
        review.setText(dto.getText());
        review.setRating(dto.getRating());

        return review;
    }

}
