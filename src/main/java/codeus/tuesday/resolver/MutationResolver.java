package codeus.tuesday.resolver;

import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import codeus.tuesday.repository.AuthorRepository;
import codeus.tuesday.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationResolver {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public MutationResolver(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * TODO: Exercise 5
     *
     * @throws IllegalArgumentException if validation fails
     */
    @MutationMapping
    public Book addBook(
            @Argument String title,
            @Argument String authorId,
            @Argument Integer publishYear,
            @Argument String genre) {
        throw new RuntimeException();
    }

    /**
     * TODO: Exercise 6
     *
     * @throws IllegalArgumentException if validation fails
     */
    @MutationMapping
    public Review addReview(
            @Argument String bookId,
            @Argument Integer rating,
            @Argument String comment) {
        throw new RuntimeException();
    }

    /**
     * TODO: Exercise 7 (Bonus)
     */
    @MutationMapping
    public Book updateBook(
            @Argument String id,
            @Argument String title,
            @Argument Integer publishYear,
            @Argument String genre) {
        throw new RuntimeException();
    }
}
