package codeus.tuesday.repository;

import codeus.tuesday.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(Long id);

    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();

    Optional<Author> findByName(String name);

    @Query("""
        SELECT a FROM Author a 
        WHERE (SELECT COUNT(b) FROM Book b WHERE b.author = a) >= :minBooks
        """)
    List<Author> findAuthorsWithMinBooks(int minBooks);

    @Query("""
        SELECT a FROM Author a 
        WHERE EXISTS (
            SELECT 1 FROM Book b 
            WHERE b.author = a AND b.genre = :genre
        )
        """)
    List<Author> findAuthorsByBookGenre(String genre);
}
