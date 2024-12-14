package com.example.snakegame2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GamePanel extends StackPane {
    private Canvas canvas;
    private GraphicsContext gc;
    private Snake snake;
    private Apple apple;
    private int width, height;
    private boolean gameOver = false;

    private static final int SLOW_SPEED = 5; // Медленный режим
    private static final int MEDIUM_SPEED = 10; // Средний режим
    private static final int FAST_SPEED = 15; // Быстрый режим

    private int speed = MEDIUM_SPEED; // Текущая скорость (средний режим по умолчанию)
    private int currentSpeedMode = 1; // 0 = медленный, 1 = средний, 2 = быстрый

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.snake = new Snake(width / 2, height / 2);
        this.apple = new Apple(width, height);
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        setFocusTraversable(true);

        // Обрабатываем нажатия клавиш
        setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        // Автоматически восстанавливаем фокус, если он теряется
        setOnMouseClicked(event -> requestFocus());
    }

    // Обработка нажатий клавиш
    private void handleKeyPress(KeyCode keyCode) {
        if (!gameOver) {
            switch (keyCode) {
                case W -> snake.setDirection(0, -1);
                case S -> snake.setDirection(0, 1);
                case A -> snake.setDirection(-1, 0);
                case D -> snake.setDirection(1, 0);
                case UP -> switchSpeedMode(true); // Увеличиваем скорость
                case DOWN -> switchSpeedMode(false); // Уменьшаем скорость
            }
        }
    }

    // Переключение режима скорости
    private void switchSpeedMode(boolean increase) {
        if (increase) {
            currentSpeedMode = (currentSpeedMode + 1) % 3; // Переключение вперёд
        } else {
            currentSpeedMode = (currentSpeedMode + 2) % 3; // Переключение назад
        }
        updateSpeed(); // Обновляем скорость
    }

    // Обновление текущей скорости в зависимости от режима
    private void updateSpeed() {
        switch (currentSpeedMode) {
            case 0 -> speed = SLOW_SPEED; // Медленный режим
            case 1 -> speed = MEDIUM_SPEED; // Средний режим
            case 2 -> speed = FAST_SPEED; // Быстрый режим
        }
    }

    // Игровой цикл
    public void gameLoop() {
        if (!gameOver) {
            gameOver = snake.move(width, height); // Двигаем змейку с учетом отражений

            // Проверка, съела ли змейка яблоко
            if (apple.isEaten(snake)) {
                snake.grow();
                apple.generateNewApple(width, height);
            }

            draw(); // Обновляем экран
        }
    }

    // Метод отрисовки на экране
    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        if (gameOver) {
            displayGameOver();
        } else {
            snake.draw(gc); // Рисуем змейку
            apple.draw(gc); // Рисуем яблоко
        }
    }

    // Отображение сообщения о конце игры
    private void displayGameOver() {
        gc.setFill(Color.RED);
        gc.setFont(javafx.scene.text.Font.font(30));
        gc.fillText("Game Over", width / 2 - 100, height / 2);
    }

    // Геттер для поля speed
    public int getSpeed() {
        return speed;
    }
}
