package codeus.tuesday.config;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;

@Configuration
public class GraphQLConfig {

    @Bean
    public DataFetcherExceptionResolverAdapter exceptionResolver() {
        return new DataFetcherExceptionResolverAdapter() {
            @Override
            protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
                if (ex instanceof IllegalArgumentException) {
                    return GraphQLError.newError()
                            .message(ex.getMessage())
                            .errorType(ErrorType.BAD_REQUEST)
                            .path(env.getExecutionStepInfo().getPath())
                            .location(env.getField().getSourceLocation())
                            .build();
                }
                return null;
            }
        };
    }
}