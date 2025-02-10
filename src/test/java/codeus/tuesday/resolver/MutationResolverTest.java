package codeus.tuesday.resolver;

import codeus.tuesday.TestDataConfig;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestDataConfig.class)
@AutoConfigureGraphQlTester
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
                    .expect(error ->
                            error.getMessage().contains("Title cannot be empty")
                    );
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
                            bookId: "3"
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
                                    "title": "The Last Case"
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
                                .hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .contains("Rating must be between 1 and 5");
                    });
        }
    }

    @Nested
    @DisplayName("Mutation: updateBook")
    class UpdateBookTests {

        @Test
        @DisplayName("Should successfully update all fields of a book")
        void shouldUpdateAllFields() {
            String mutation = """
                    mutation {
                        updateBook(
                            id: "1",
                            title: "Updated Title",
                            publishYear: 2022,
                            genre: "Updated Genre"
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
                    .path("updateBook")
                    .matchesJson("""
                            {
                                "id": "1",
                                "title": "Updated Title",
                                "publishYear": 2022,
                                "genre": "Updated Genre",
                                "author": {
                                    "name": "John Doe"
                                }
                            }
                            """);
        }

        @Test
        @DisplayName("Should partially update book when some fields are null")
        void shouldPartiallyUpdateBook() {
            String mutation = """
                    mutation {
                        updateBook(
                            id: "2",
                            title: "New Title"
                        ) {
                            id
                            title
                            publishYear
                            genre
                        }
                    }
                    """;

            graphQlTester.document(mutation)
                    .execute()
                    .path("updateBook")
                    .matchesJson("""
                            {
                                "id": "2",
                                "title": "New Title",
                                "publishYear": 2021,
                                "genre": "Science Fiction"
                            }
                            """);
        }

        @Test
        @DisplayName("Should fail when book ID doesn't exist")
        void shouldFailWithNonExistentBook() {
            String mutation = """
                    mutation {
                        updateBook(
                            id: "999",
                            title: "New Title"
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
                                .hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .isEqualTo("Book not found");
                    });
        }

        @Test
        @DisplayName("Should fail when publish year is in the future")
        void shouldFailWithFuturePublishYear() {
            String mutation = """
                    mutation {
                        updateBook(
                            id: "1",
                            publishYear: 2026
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
                                .hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .isEqualTo("Publish year cannot be in the future");
                    });
        }

        @Test
        @DisplayName("Should fail when title is empty")
        void shouldFailWithEmptyTitle() {
            String mutation = """
                    mutation {
                        updateBook(
                            id: "1",
                            title: ""
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
                                .hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .isEqualTo("Title cannot be empty");
                    });
        }
    }

    @Data
    static class BookResponse {
        private String id;
        private String title;
        private Integer publishYear;
        private String genre;
        private BookResolverTest.AuthorResponse author;
    }
}