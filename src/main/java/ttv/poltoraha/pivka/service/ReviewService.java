package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.dto.request.ReviewRequestDto;

public interface ReviewService {
    public void createReview(ReviewRequestDto requestDto);
    public void deleteReview(Integer reviewId);
    public void updateReview(Integer reviewId, ReviewRequestDto requestDto);
}
