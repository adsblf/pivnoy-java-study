package ttv.poltoraha.pivka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Quote;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<Quote,Long> {
    public Optional<Quote> findById(Long id);

    public List<Quote> findAll();
}
