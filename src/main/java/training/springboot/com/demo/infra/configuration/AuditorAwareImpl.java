package training.springboot.com.demo.infra.configuration;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String CREATED_UPDATED_BY = "Service";

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(CREATED_UPDATED_BY);
    }
}
