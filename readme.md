# GraphQL Workshop - Library Management System

## Overview
This workshop provides hands-on experience with GraphQL in Java using Spring Boot. You'll implement a simple library management system with books, authors, and reviews.

## Goal
Complete the implementation of GraphQL resolvers for querying and mutating library data. The project structure is pre-configured, and your task is to implement the missing functionality in the resolver classes.

## Prerequisites
- Java 17
- Maven
- IDE (IntelliJ IDEA recommended)
- Basic knowledge of Spring Boot
- Basic understanding of GraphQL concepts

## Your Tasks

### 1. Implement Book Queries
File: `BookResolver.java`
- Implement `bookById(String id)` method
- Implement `allBooks()` method
- Implement `author(Book book)` method
- Implement `reviews(Book book)` method

### 2. Implement Mutations
File: `MutationResolver.java`
- Implement `addBook()` method
- Implement `addReview()` method
- (Bonus) Implement `updateBook()` method

## Testing Your Implementation

### Running Tests

- File: `BookResolverTest.java`
- File: `MutationResolverTest.java`

## GraphQL Schema
[schema.graphqls](src%2Fmain%2Fresources%2Fgraphql%2Fschema.graphqls)

## Validation Requirements
### For Books:
- Title cannot be empty
- PublishYear cannot be in the future
- Author must exist in the system
### For Reviews:
- Rating must be between 1 and 5
- Book must exist in the system

## Reference Implementation
The complete solution can be found in the `solution` branch. However, try to implement the functionality yourself first!

## Common Issues and Solutions
- Remember to convert String IDs to Long when querying repositories
- Use proper exception handling for validation errors
- Ensure proper null handling in resolvers
- Check for entity existence before creating relationships

## Additional Resources
- [Spring GraphQL Documentation](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [GraphQL Java Documentation](https://www.graphql-java.com/documentation/getting-started)

## Need Help?
- Check the test files for expected behavior
- Review the validation requirements
- Look at the GraphQL schema for type definitions
- Consult the example queries above

Good luck with your implementation! 🚀