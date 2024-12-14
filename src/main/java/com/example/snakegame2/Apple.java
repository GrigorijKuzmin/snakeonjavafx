package com.example.snakegame2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Apple {
    Point position;
    int size = 20;

    public Apple(int width, int height) {
        generateNewApple(width, height); // Генерация нового яблока
    }

    // Рисуем яблоко
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.x, position.y, size, size);
    }

    // Проверяем, съела ли змейка яблоко
    public boolean isEaten(Snake snake) {
        if (snake.body.isEmpty()) {
            return false; // Если список пуст, просто возвращаем false
        }
        return snake.body.getFirst().equals(position);
    }

    // Генерируем новое яблоко
    public void generateNewApple(int width, int height) {
        Random rand = new Random();
        position = new Point(rand.nextInt(width / size) * size, rand.nextInt(height / size) * size);
    }
}
