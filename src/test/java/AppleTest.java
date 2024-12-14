package com.example.snakegame2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppleTest {
    @Test
    void testSnakeGrowsAfterEatingApple() {
        Snake snake = new Snake(100, 100);
        Apple apple = new Apple(800, 600);

        // Помещаем яблоко на голову змейки
        apple.position = new Point(100, 100);

        int initialLength = snake.body.size();

        // Проверяем, что змейка съела яблоко и выросла
        if (apple.isEaten(snake)) {
            snake.grow();
        }
        assertEquals(initialLength + 1, snake.body.size(), "Длина змейки должна увеличиться на 1");
    }
}
