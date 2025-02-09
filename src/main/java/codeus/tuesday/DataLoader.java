package codeus.tuesday;

import codeus.tuesday.model.Author;
import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import codeus.tuesday.repository.AuthorRepository;
import codeus.tuesday.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    //AI generated me this piece of code instead of normal migration so let it be lmao

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            // Create authors
            Author author1 = new Author("John Doe", "Famous mystery writer");
            Author author2 = new Author("Jane Smith", "Science fiction author");

            authorRepository.save(author1);
            authorRepository.save(author2);

            // Create books
            Book book1 = new Book("The Mystery House", author1, 2020, "Mystery");
            Book book2 = new Book("Space Adventures", author2, 2021, "Science Fiction");
            Book book3 = new Book("The Last Case", author1, 2022, "Mystery");

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

            // Add reviews
            book1.getReviews().add(new Review(5, "Great book!", book1));
            book1.getReviews().add(new Review(4, "Very entertaining", book1));
            book2.getReviews().add(new Review(5, "Couldn't put it down", book2));

            bookRepository.save(book1);
            bookRepository.save(book2);
        };
    }
}