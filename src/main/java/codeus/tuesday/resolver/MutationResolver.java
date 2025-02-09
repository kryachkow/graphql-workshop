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
     * Implement mutation to add a new book
     * Requirements:
     * - Validate that title is not empty
     * - Verify that author exists
     * - PublishYear should not be in the future
     * - Save the book and return it
     *
     * @throws IllegalArgumentException if validation fails
     */
    @MutationMapping
    public Book addBook(
            @Argument String title,
            @Argument String authorId,
            @Argument Integer publishYear,
            @Argument String genre) {
        // Add your code here
        throw new UnsupportedOperationException("Implement addBook method");
    }

    /**
     * TODO: Exercise 6
     * Implement mutation to add a review to a book
     * Requirements:
     * - Rating should be between 1 and 5
     * - Verify that book exists
     * - Save the review and return it
     *
     * @throws IllegalArgumentException if validation fails
     */
    @MutationMapping
    public Review addReview(
            @Argument String bookId,
            @Argument Integer rating,
            @Argument String comment) {
        // Add your code here
        throw new UnsupportedOperationException("Implement addReview method");
    }

    /**
     * TODO: Exercise 7 (Bonus)
     * Implement mutation to update book details
     * Requirements:
     * - Only update provided fields (handle null values)
     * - Validate publishYear if provided
     * - Return updated book
     */
    @MutationMapping
    public Book updateBook(
            @Argument String id,
            @Argument String title,
            @Argument Integer publishYear,
            @Argument String genre) {
        // Add your code here
        throw new UnsupportedOperationException("Implement updateBook method");
    }
}
