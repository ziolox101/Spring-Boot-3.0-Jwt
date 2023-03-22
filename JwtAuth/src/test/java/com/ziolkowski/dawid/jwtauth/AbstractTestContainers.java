package com.ziolkowski.dawid.jwtauth;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@Testcontainers
public class AbstractTestContainers {
    @Container
    protected static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("test_api")
            .withUsername("root")
            .withPassword("secret");
    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    @BeforeAll
    public static void beforeAll(){
        mysqlContainer.start();
    }
}



