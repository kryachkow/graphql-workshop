package codeus.tuesday.resolver;

import codeus.tuesday.model.Author;
import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import codeus.tuesday.repository.AuthorRepository;
import codeus.tuesday.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.Year;

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

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        Author author = authorRepository.findById(Long.valueOf(authorId))
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        if (publishYear != null && publishYear > Year.now().getValue()) {
            throw new IllegalArgumentException("Publish year cannot be in the future");
        }

        Book book = new Book(title, author, publishYear, genre);
        return bookRepository.save(book);
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

        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Book book = bookRepository.findById(Long.valueOf(bookId))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = new Review(rating, comment, book);
        book.getReviews().add(review);
        bookRepository.save(book);
        return review;
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
        Book book = bookRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (title != null && !title.trim().isEmpty()) {
            book.setTitle(title);
        }

        if (publishYear != null) {
            if (publishYear > Year.now().getValue()) {
                throw new IllegalArgumentException("Publish year cannot be in the future");
            }
            book.setPublishYear(publishYear);
        }

        if (genre != null) {
            book.setGenre(genre);
        }

        return bookRepository.save(book);
    }
}
