package com.ziolkowski.dawid.jwtauth;

import com.ziolkowski.dawid.jwtauth.Model.TestModel;
import com.ziolkowski.dawid.jwtauth.Repo.TestModelRepo;
import com.ziolkowski.dawid.jwtauth.Service.TestModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestModelServiceTest {

    private TestModelService testModelService;

    @Mock
    private TestModelRepo testModelRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testModelService = new TestModelService(testModelRepo);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<TestModel> expectedList = List.of(new TestModel("aa"), new TestModel("bb"), new TestModel("cc"));
        when(testModelRepo.findAll()).thenReturn(expectedList);

        // Act
        List<TestModel> actualList = testModelService.getAll();

        // Assert
        assertEquals(expectedList, actualList);
    }
}