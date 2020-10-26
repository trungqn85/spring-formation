package training.springboot.com.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@AutoConfigureDataJpa
@EnableJpaRepositories(basePackages = "training.springboot.com.demo.domain")
@EntityScan(basePackages = "training.springboot.com.demo.domain")
@ComponentScan("training.springboot.com.demo")
public class PersistenceTestConfig {

}
