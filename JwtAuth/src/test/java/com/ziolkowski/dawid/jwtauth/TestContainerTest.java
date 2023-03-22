package com.ziolkowski.dawid.jwtauth;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class TestContainerTest extends AbstractTestContainers {
    @Test
    public void canStartMySqlDBTest(){
        assertThat(mysqlContainer.isRunning()).isTrue();
        assertThat(mysqlContainer.isCreated()).isTrue();
    }
}