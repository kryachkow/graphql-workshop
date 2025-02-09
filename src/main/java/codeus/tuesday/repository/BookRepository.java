package codeus.tuesday.repository;

import codeus.tuesday.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.author")
    List<Book> findAllWithAuthors();

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.author LEFT JOIN FETCH b.reviews WHERE b.id = :id")
    Book findByIdWithAuthorAndReviews(Long id);

    List<Book> findByGenre(String genre);

    List<Book> findByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b WHERE b.publishYear BETWEEN :startYear AND :endYear")
    List<Book> findByPublishYearRange(Integer startYear, Integer endYear);

    boolean existsByTitleAndAuthorId(String title, Long authorId);
}
