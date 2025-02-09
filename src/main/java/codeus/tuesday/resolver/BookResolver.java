package codeus.tuesday.resolver;

import codeus.tuesday.model.Author;
import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import codeus.tuesday.repository.AuthorRepository;
import codeus.tuesday.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookResolver {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResolver(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * TODO: Exercise 1
     */
    @QueryMapping
    public Book bookById(@Argument String id) {
        throw new RuntimeException();
    }

    /**
     * TODO: Exercise 2
     */
    @QueryMapping
    public List<Book> allBooks() {
        throw new RuntimeException();
    }

    /**
     * TODO: Exercise 3
     */
    @SchemaMapping
    public Author author(Book book) {
        throw new RuntimeException();
    }

    /**
     * TODO: Exercise 4
     */
    @SchemaMapping
    public List<Review> reviews(Book book) {
        throw new RuntimeException();
    }
}
