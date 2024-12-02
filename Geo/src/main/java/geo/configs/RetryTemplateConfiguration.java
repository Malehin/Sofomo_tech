package geo.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@Slf4j
public class RetryTemplateConfiguration {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new FixedBackOffPolicy());
        retryTemplate.registerListener(retryListener());
        return retryTemplate;
    }

    @Bean
    public RetryListener retryListener() {
        return new RetryListener() {

            @Override
            public <T, E extends Throwable> void onError(
                    RetryContext context,
                    RetryCallback<T, E> callback,
                    Throwable throwable) {
                log.warn(
                        "Retry attempt {} for retryable method {} threw exception: ",
                        context.getRetryCount(),
                        context.getAttribute("context.name"),
                        throwable);
            }

        };
    }

}
