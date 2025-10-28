package ttv.poltoraha.pivka.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequestDto {
    private String fullName;
    private Double avgRating;
}
