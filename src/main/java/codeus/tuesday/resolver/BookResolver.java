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
     * Implement a query to fetch a book by its ID
     * - Use bookRepository.findById()
     * - Convert the ID from String to Long
     * - Return null if book is not found
     */
    @QueryMapping
    public Book bookById(@Argument String id) {
        // Add your code here
        throw new UnsupportedOperationException("Implement bookById method");
    }

    /**
     * TODO: Exercise 2
     * Implement a query to fetch all books
     * - Use bookRepository.findAll()
     * - Return List<Book>
     */
    @QueryMapping
    public List<Book> allBooks() {
        // Add your code here
        throw new UnsupportedOperationException("Implement allBooks method");
    }

    /**
     * TODO: Exercise 3
     * Implement a resolver for Book.author field
     * - This method will be called for each Book when author field is requested
     * - Use authorRepository to fetch the author
     * - Handle the case when author is not found
     */
    @SchemaMapping
    public Author author(Book book) {
        // Add your code here
        throw new UnsupportedOperationException("Implement author method");
    }

    /**
     * TODO: Exercise 4
     * Implement a resolver for Book.reviews field
     * - Return all reviews for a given book
     * - Reviews should be sorted by rating (highest first)
     */
    @SchemaMapping
    public List<Review> reviews(Book book) {
        // Add your code here
        throw new UnsupportedOperationException("Implement reviews method");
    }
}
