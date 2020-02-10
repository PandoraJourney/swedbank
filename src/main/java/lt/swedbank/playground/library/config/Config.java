package lt.swedbank.playground.library.config;

import lt.swedbank.playground.library.sites.manager.DoubleQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@ImportResource("classpath:playground.xml")
public class Config {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public DoubleQueue retrieveDoubleQueue() {
        return new DoubleQueue();
    }
}
