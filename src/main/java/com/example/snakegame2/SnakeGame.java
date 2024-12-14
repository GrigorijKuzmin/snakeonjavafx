package com.example.snakegame2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    @Override
    public void start(Stage primaryStage) {
        GamePanel gamePanel = new GamePanel(800, 600);
        Scene scene = new Scene(gamePanel, 800, 600);

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Устанавливаем фокус на игровую панель
        gamePanel.requestFocus();

        // Игровой цикл
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000 / gamePanel.getSpeed()) {
                    gamePanel.gameLoop();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
