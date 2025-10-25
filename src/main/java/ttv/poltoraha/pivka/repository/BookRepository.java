package ttv.poltoraha.pivka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
