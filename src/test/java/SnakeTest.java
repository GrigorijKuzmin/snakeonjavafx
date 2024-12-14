package com.example.snakegame2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    @Test
    void testSnakeReflectsOffBoundary() {
        Snake snake = new Snake(0, 0); // Стартовая позиция у верхнего левого угла
        snake.setDirection(-1, 0); // Двигаем змейку влево

        // Двигаем змейку и проверяем отражение
        boolean collision = snake.move(800, 600);
        assertFalse(collision, "Змейка не должна погибнуть при отражении");
        assertEquals(20, snake.body.getFirst().x, "Змейка должна отразиться и двигаться вправо");
    }
}
