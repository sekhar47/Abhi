package com.example.emailverify;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.TaskScheduler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskSchedulerConfigTest {

    @Test
    public void testTaskSchedulerBeanCreation() {
        // Arrange
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskSchedulerConfig.class)) {
            // Act
            TaskScheduler taskScheduler = context.getBean(TaskScheduler.class);

            // Assert
            assertNotNull(taskScheduler);
        }
    }
}
