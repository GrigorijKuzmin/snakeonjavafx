package com.example.snakegame2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Snake {
    LinkedList<Point> body;
    int directionX, directionY;
    int size = 20; // Размер клетки
    boolean justReflected = false; // Флаг для отслеживания отражения
    int invulnerabilityDuration = 3; // Длительность неуязвимости в кадрах
    int invulnerabilityCounter = 0; // Счетчик неуязвимости

    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY)); // Начальная позиция змейки
        directionX = 1; // Начальное направление по оси X
        directionY = 0; // Начальное направление по оси Y
    }

    // Двигаем змейку с учетом отражения от границ
    public boolean move(int width, int height) {
        if (body.isEmpty()) {
            return false; // Если список пуст, просто возвращаем false
        }

        Point head = body.getFirst();
        Point newHead = new Point(head.x + directionX * size, head.y + directionY * size);

        // Проверка на столкновение с границей и отражение
        if (isCollidingWithBoundary(newHead, width, height)) {
            newHead = reflectOffBoundary(head, width, height);
        }

        // Проверка на столкновение с собой
        if (invulnerabilityCounter == 0 && selfCollision(newHead)) {
            return true; // Возвращаем true, если произошло столкновение с собой
        }

        body.addFirst(newHead); // Добавляем новую голову
        body.removeLast(); // Убираем хвост змейки

        if (invulnerabilityCounter > 0) {
            invulnerabilityCounter--; // Уменьшаем счетчик неуязвимости
        }

        justReflected = false; // Сбрасываем флаг отражения
        return false; // Возвращаем false, если столкновения с собой не произошло
    }

    // Проверка столкновения с границами
    private boolean isCollidingWithBoundary(Point newHead, int width, int height) {
        return newHead.x < 0 || newHead.x >= width || newHead.y < 0 || newHead.y >= height;
    }

    // Отражение змейки от границ
    private Point reflectOffBoundary(Point head, int width, int height) {
        if (head.x < 0 || head.x >= width) {
            directionX = -directionX;
        }
        if (head.y < 0 || head.y >= height) {
            directionY = -directionY;
        }
        justReflected = true;
        invulnerabilityCounter = invulnerabilityDuration;
        return new Point(head.x + directionX * size, head.y + directionY * size);
    }

    // Изменяем направление
    public void setDirection(int dx, int dy) {
        // Проверяем, чтобы новое направление не было противоположным текущему
        if (dx != -directionX || dy != -directionY) {
            directionX = dx;
            directionY = dy;
        }
    }

    // Проверяем, не столкнулась ли змейка сама с собой
    private boolean selfCollision(Point newHead) {
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(newHead)) {
                return true;
            }
        }
        return false;
    }

    // Увеличиваем змейку при съедении яблока
    public void grow() {
        if (body.isEmpty()) {
            return; // Если список пуст, просто возвращаем
        }

        Point tail = body.getLast();
        body.addLast(new Point(tail.x, tail.y)); // Добавляем сегмент в конец
    }

    // Рисуем змейку
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        for (Point segment : body) {
            gc.fillRect(segment.x, segment.y, size, size);
        }
    }
}
