package codeus.tuesday;

import codeus.tuesday.model.Author;
import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import codeus.tuesday.repository.AuthorRepository;
import codeus.tuesday.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@TestConfiguration
public class TestDataConfig {

    @Bean
    CommandLineRunner testDataLoader(BookRepository bookRepository, AuthorRepository authorRepository) {
        return args -> {
            // Clear existing data
            bookRepository.deleteAll();
            authorRepository.deleteAll();

            // Create authors
            Author author1 = new Author("John Doe", "Famous mystery writer");
            Author author2 = new Author("Jane Smith", "Science fiction author");

            author1 = authorRepository.save(author1);
            author2 = authorRepository.save(author2);

            // Create books
            Book book1 = new Book("The Mystery House", author1, 2020, "Mystery");
            Book book2 = new Book("Space Adventures", author2, 2021, "Science Fiction");
            Book book3 = new Book("The Last Case", author1, 2022, "Mystery");

            book1 = bookRepository.save(book1);
            book2 = bookRepository.save(book2);
            book3 = bookRepository.save(book3);

            // Add reviews
            Review review1 = new Review(5, "Great book!", book1);
            Review review2 = new Review(4, "Very good", book1);
            Review review3 = new Review(3, "Decent read", book2);

            book1.getReviews().addAll(Arrays.asList(review1, review2));
            book2.getReviews().add(review3);

            bookRepository.saveAll(Arrays.asList(book1, book2));
        };
    }
}
