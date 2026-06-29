package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import com.example.scp.config.properties.BonbonProperties;
import com.example.sibling.SiblingBonbonComponent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(AbstractE2eConfiguration.Config.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties({BonbonProperties.class})
@PropertySource("classpath:bonbon.properties")
@PropertySource(value = "classpath:not-existed.properties", ignoreResourceNotFound = true)
public abstract class AbstractE2eConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractE2eConfiguration.class);

    @TestConfiguration
    public static class Config {
        @Bean
        @ServiceConnection
        public PostgreSQLContainer<?> postgresContainer() {
            return new PostgreSQLContainer<>("postgres:16-alpine");
        }

        @Bean
        public BonbonComponent bonbonComponent(MeterRegistry meterRegistry, SiblingBonbonComponent siblingBonbonComponent) {
            return new BonbonComponent(siblingBonbonComponent) {
                @Override
                public String comCheck() {
                    meterRegistry.counter("bonbon-counter", "counter", "com check").increment();
                    return "Iam test Houston";
                }
            };
        }

        @Bean
        public Counter counter(MeterRegistry meterRegistry) {
            return meterRegistry.counter("bonbon-counter", "counter", "com check");
        }

        /**
         * Global tag
         */
        @Bean
        public MeterRegistryCustomizer<MeterRegistry> meterRegistryMeterRegistryCustomizer() {
            return registry -> registry.config().commonTags("houston", "com, check");
        }

        @Bean
        public CommandLineRunner commandLineRunner() {
            return args -> {
                LOGGER.info("CommandLineRunner is called: {}", (Object[]) args);
            };
        }

        @Bean
        public ApplicationRunner applicationRunner() {
            return args -> {
                LOGGER.info("ApplicationRunner is called: {}", (Object[]) args.getSourceArgs());
            };
        }
    }

}
