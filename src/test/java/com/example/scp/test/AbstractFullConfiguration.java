package com.example.scp.test;

import com.example.scp.component.BonbonComponent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(AbstractFullConfiguration.Config.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "create table if not exists bon_bon (id integer, candy_type varchar(255));"
})
public abstract class AbstractFullConfiguration {

    @TestConfiguration
    public static class Config {
        @Bean
        @ServiceConnection
        public PostgreSQLContainer<?> postgresContainer() {
            return new PostgreSQLContainer<>("postgres:16-alpine");
        }

        @Bean
        public BonbonComponent bonbonComponent() {
            return new BonbonComponent() {
                @Override
                public String comCheck() {
                    return "Iam test Houston";
                }
            };
        }
    }

}
