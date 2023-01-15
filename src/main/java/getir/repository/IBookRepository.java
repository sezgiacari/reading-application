package getir.repository;

import getir.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByNameAndWriterAndPrice(String name, String writer, Double price);

}
