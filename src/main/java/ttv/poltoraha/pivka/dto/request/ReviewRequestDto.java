package ttv.poltoraha.pivka.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDto {
    private String readerUsername;
    private Integer bookId;
    private String text;
    private Integer rating;
}
