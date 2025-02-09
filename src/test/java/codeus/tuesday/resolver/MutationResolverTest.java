package codeus.tuesday.resolver;

import codeus.tuesday.TestDataConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestDataConfig.class)
class MutationResolverTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Nested
    @DisplayName("Mutation: addBook")
    class AddBookTests {
        @Test
        @DisplayName("Should successfully add a new book")
        void shouldAddNewBook() {
            String mutation = """
                mutation {
                    addBook(
                        title: "New Test Book"
                        authorId: "1"
                        publishYear: 2023
                        genre: "Fiction"
                    ) {
                        id
                        title
                        publishYear
                        genre
                        author {
                            name
                        }
                    }
                }
                """;

            graphQlTester.document(mutation)
                    .execute()
                    .path("addBook")
                    .matchesJson("""
                    {
                        "title": "New Test Book",
                        "publishYear": 2023,
                        "genre": "Fiction",
                        "author": {
                            "name": "John Doe"
                        }
                    }
                    """);
        }

        @Test
        @DisplayName("Should fail when adding book with empty title")
        void shouldFailWithEmptyTitle() {
            String mutation = """
                mutation {
                    addBook(
                        title: ""
                        authorId: "1"
                        publishYear: 2023
                        genre: "Fiction"
                    ) {
                        id
                    }
                }
                """;

            graphQlTester.document(mutation)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors)
                                .hasSize(1)
                                .first()
                                .satisfies(error ->
                                        assertThat(error.getMessage())
                                                .contains("Title cannot be empty")
                                );
                    });
        }
    }

    @Nested
    @DisplayName("Mutation: addReview")
    class AddReviewTests {
        @Test
        @DisplayName("Should successfully add a review")
        void shouldAddReview() {
            String mutation = """
                mutation {
                    addReview(
                        bookId: "1"
                        rating: 5
                        comment: "Excellent read!"
                    ) {
                        rating
                        comment
                        book {
                            title
                        }
                    }
                }
                """;

            graphQlTester.document(mutation)
                    .execute()
                    .path("addReview")
                    .matchesJson("""
                    {
                        "rating": 5,
                        "comment": "Excellent read!",
                        "book": {
                            "title": "The Mystery House"
                        }
                    }
                    """);
        }

        @Test
        @DisplayName("Should fail when rating is invalid")
        void shouldFailWithInvalidRating() {
            String mutation = """
                mutation {
                    addReview(
                        bookId: "1"
                        rating: 6
                        comment: "Invalid rating"
                    ) {
                        id
                    }
                }
                """;

            graphQlTester.document(mutation)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors)
                                .hasSize(1)
                                .first()
                                .satisfies(error ->
                                        assertThat(error.getMessage())
                                                .contains("Rating must be between 1 and 5")
                                );
                    });
        }
    }
}