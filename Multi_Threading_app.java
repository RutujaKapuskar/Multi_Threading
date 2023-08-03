/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package multi_threading_app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author RK
 */
public class Multi_Threading_app extends Application {
    
   
 public static void main(String[] args) {
        launch(args);
    }

    private Canvas rk_canvas;

    private volatile boolean rk_running;

    private rk_Runner rk_runner;
    private Button rk_startButton;

    // a button that is used to start and stop the animation

 @Override
    public void start(Stage stage) {
        rk_canvas = new Canvas(640, 480);
        rk_redraw();
        rk_startButton = new Button("Start!");
        rk_startButton.setOnAction(e -> rk_doStartOrStop());
        HBox rk_bottom = new HBox(rk_startButton);
        rk_bottom.setStyle("-fx-padding:6px; -fx-border-color:black; -fx-border-width:3px 0 0 0");
        rk_bottom.setAlignment(Pos.CENTER);
        BorderPane rkRoot = new BorderPane(rk_canvas);
        rkRoot.setBottom(rk_bottom);
        Scene rk_scene = new Scene(rkRoot);
        stage.setScene(rk_scene);
        stage.setTitle("CLICK START TO MAKE RANDOM ART!");
        stage.setResizable(false);
        stage.show();
    }

    private class rk_Runner extends Thread {
        @Override
        public void run() {
            while (rk_running) {
                Platform.runLater(() -> rk_redraw());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    private void rk_redraw() {
        GraphicsContext rk_g = rk_canvas.getGraphicsContext2D();
        double rk_width = rk_canvas.getWidth();
        double rk_height = rk_canvas.getHeight();

        if (!rk_running) {
            rk_g.setFill(Color.WHITE);
            rk_g.fillRect(0, 0, rk_width, rk_height);
            return;
        }
        Color rk_randomGray = Color.hsb(1, 0, Math.random());
        rk_g.setFill(rk_randomGray);
        rk_g.fillRect(0, 0, rk_width, rk_height);
        int rk_artType = (int) (3 * Math.random());

        switch (rk_artType) {
            case 0:
                rk_g.setLineWidth(2);
                for (int i = 0; i < 500; i++) {
                    int x1 = (int) (rk_width * Math.random());
                    int y1 = (int) (rk_height * Math.random());
                    int x2 = (int) (rk_width * Math.random());
                    int y2 = (int) (rk_height * Math.random());
                    Color randomHue = Color.hsb(360 * Math.random(), 1, 1);
                    rk_g.setStroke(randomHue);
                    rk_g.strokeLine(x1, y1, x2, y2);
                }
                break;
            case 1:
                for (int i = 0; i < 200; i++) {
                    int rk_centerX = (int) (rk_width * Math.random());
                    int rk_centerY = (int) (rk_height * Math.random());
                    Color rk_randomHue = Color.hsb(360 * Math.random(), 1, 1);
                    rk_g.setStroke(rk_randomHue);
                    rk_g.strokeLine(rk_centerX - 50, rk_centerY - 50, 100, 100);
                }
                break;
            default:
                rk_g.setStroke(Color.BLACK);
                rk_g.setLineWidth(4);
                for (int i = 0; i < 25; i++) {
                    int centerX = (int) (rk_width * Math.random());
                    int centerY = (int) (rk_height * Math.random());
                    int size = 30 + (int) (170 * Math.random());
                    Color randomColor = Color.hsb(360 * Math.random(), Math.random(), Math.random());
                    rk_g.setFill(randomColor);
                    rk_g.fillRect(centerX - size / 2, centerY - size / 2, size, size);
                    rk_g.strokeRect(centerX - size / 2, centerY - size / 2, rk_artType, rk_height);
                }
                break;

        }
    }

    private void rk_doStartOrStop() {
        if (rk_running == false) {
            rk_startButton.setText("Stop!");
            rk_runner = new rk_Runner();
            rk_running = true;
            rk_runner.start();
        } else {
            rk_startButton.setDisable(true);
            rk_running = false;
            rk_redraw();
            rk_runner.interrupt();

            try {
                rk_runner.join(1000);
            } catch (InterruptedException e) {

            }
            rk_runner = null;
            rk_startButton.setText("Start!");
            rk_startButton.setDisable(false);

        }
    }
    
}
