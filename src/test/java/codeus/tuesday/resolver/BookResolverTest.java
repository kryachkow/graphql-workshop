package codeus.tuesday.resolver;

import codeus.tuesday.TestDataConfig;
import codeus.tuesday.model.Book;
import codeus.tuesday.model.Review;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestDataConfig.class)
class BookResolverTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Nested
    @DisplayName("Query: bookById")
    class BookByIdTests {
        @Test
        @DisplayName("Should return book when valid ID is provided")
        void shouldReturnBookById() {
            String query = """
                query {
                    bookById(id: "1") {
                        id
                        title
                        author {
                            name
                        }
                    }
                }
                """;

            graphQlTester.document(query)
                    .execute()
                    .path("bookById")
                    .matchesJson("""
                    {
                        "id": "1",
                        "title": "The Mystery House",
                        "author": {
                            "name": "John Doe"
                        }
                    }
                    """);
        }

        @Test
        @DisplayName("Should return null when book ID doesn't exist")
        void shouldReturnNullForNonExistentBook() {
            String query = """
                query {
                    bookById(id: "999") {
                        id
                        title
                    }
                }
                """;

            graphQlTester.document(query)
                    .execute()
                    .path("bookById")
                    .valueIsNull();
        }
    }

    @Nested
    @DisplayName("Query: allBooks")
    class AllBooksTests {
        @Test
        @DisplayName("Should return all books")
        void shouldReturnAllBooks() {
            String query = """
                query {
                    allBooks {
                        id
                        title
                        author {
                            name
                        }
                    }
                }
                """;

            graphQlTester.document(query)
                    .execute()
                    .path("allBooks")
                    .entityList(BookResponse.class)
                    .hasSize(3)
                    .satisfies(books -> {
                        assertThat(books)
                                .extracting(BookResponse::getTitle)
                                .contains("The Mystery House", "Space Adventures", "The Last Case");
                    });
        }
    }

    @Nested
    @DisplayName("SchemaMapping: author field")
    class AuthorFieldTests {
        @Test
        @DisplayName("Should return author details for book")
        void shouldReturnAuthorForBook() {
            String query = """
                query {
                    bookById(id: "1") {
                        title
                        author {
                            id
                            name
                            biography
                        }
                    }
                }
                """;

            graphQlTester.document(query)
                    .execute()
                    .path("bookById.author")
                    .matchesJson("""
                    {
                        "id": "1",
                        "name": "John Doe",
                        "biography": "Famous mystery writer"
                    }
                    """);
        }
    }

    @Nested
    @DisplayName("SchemaMapping: reviews field")
    class ReviewsFieldTests {
        @Test
        @DisplayName("Should return sorted reviews for book")
        void shouldReturnSortedReviews() {
            String query = """
                query {
                    bookById(id: "1") {
                        reviews {
                            rating
                            comment
                        }
                    }
                }
                """;

            graphQlTester.document(query)
                    .execute()
                    .path("bookById.reviews")
                    .entityList(ReviewResponse.class)
                    .satisfies(reviews -> {
                        assertThat(reviews)
                                .extracting(ReviewResponse::getRating)
                                .isSortedAccordingTo((r1, r2) -> r2.compareTo(r1));
                    });
        }
    }

    @Data
    class BookResponse {
        private String id;
        private String title;
        private AuthorResponse author;
        private List<ReviewResponse> reviews;
    }

    @Data
    class AuthorResponse {
        private String id;
        private String name;
        private String biography;
    }

    @Data
    class ReviewResponse {
        private String id;
        private Integer rating;
        private String comment;
    }
}

