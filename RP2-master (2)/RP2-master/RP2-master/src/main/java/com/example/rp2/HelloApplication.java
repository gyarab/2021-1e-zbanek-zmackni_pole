package com.example.rp2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class HelloApplication extends Application {
    static Pane pane = new Pane();
    static VBox vbox = new VBox();
    static Label skorel = new Label();
    static int skore = 0;
    static Random rand = new Random();
    static int x = 500;
    static int y = 500;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);

        Scene menu = new Scene(vbox, x, y);
        Scene hra = new Scene(pane, x, y);

        pane.getChildren().add(skorel);

        ObservableList<String> obtiznosti =
                FXCollections.observableArrayList(
                        "Hard",
                        "Medium",
                        "Easy"
                );
        ComboBox comboBox = new ComboBox(obtiznosti);
        comboBox.setPromptText("Obtížnost");

        Button obtiznosti1 = new Button("Play !");
        obtiznosti1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                if (comboBox.getValue() == null ){
                    Label error = new Label("Vyberte obtížnost");
                    vbox.getChildren().addAll(error);
                }else{
                    stage.setScene(hra);
                    stage.setTitle("Hra");
                    StartTimer((String) comboBox.getValue(), stage, menu);
                }
            }
        });

        Label nadpis = new Label("Zmáčkni pole");

        vbox.getChildren().addAll(nadpis, comboBox, obtiznosti1);
        vbox.setAlignment(Pos.CENTER);

        String css = this.getClass().getResource("styly.css").toExternalForm();
        hra.getStylesheets().add(css);

        stage.setTitle("Menu");
        stage.setScene(menu);

        skorel.getStyleClass().add("skore");
        skorel.setLayoutX(x/2);
        skorel.setLayoutY(y/2);

        stage.show();

    }

    public static void main(String[] args) {

        launch();
    }

    public static void novyButton() {
        Button btn = new Button("");
        pane.getChildren().add(btn);
        btn.setLayoutX(rand.nextInt(x - 50));
        btn.setLayoutY(rand.nextInt(y - 50));
        btn.setPrefWidth(50);
        btn.setPrefHeight(50);
        btn.getStyleClass().add("batn");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                pane.getChildren().remove(btn);
                skore++;
            }

        });
    }

    public static void StartTimer(String obtiznost, Stage stage, Scene menu) {
        final int[] cas = {60,0};
        final long startNanoTime = System.nanoTime();
        Label casomira = new Label(Integer.toString(cas[0]));
        pane.getChildren().add(casomira);
        double kolikrat = 0;
        pane.setStyle("-fx-background-color: #E0FFFF;");
        if (obtiznost == "Hard") kolikrat = 2.1;
        else if (obtiznost == "Medium") kolikrat = 1.4;
        else if (obtiznost == "Easy") kolikrat = 0.69;
        double finalKolikrat = kolikrat;
        new AnimationTimer() {
            int i = 0;

            public void handle(long currentNanoTime) {

                int[] hs = new int[1];

                if (cas[1] < 60){
                    cas[1]++;
                }else if (cas[1] == 60){
                    cas[1] = 0;
                    cas[0]--;
                }

                if (cas[0] == 0) {

                    hs[0] = skore;
                    stage.setScene(menu);
                    this.stop();


                    if (hs[0] < skore) {
                        hs[0] = skore;

                       /* try {
                            File file = new File("src/main/resources/com/example/rp2/hs.txt");
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line = reader.readLine();
                            while (line != null)
                            {
                                try {
                                    int score = Integer.parseInt(line.trim());
                                    if (score > hs[0])
                                    {
                                        Label hs1 = new Label("tvé highscore je" + score);
                                        hs1.setText(Integer.toString(score));
                                        vbox.getChildren().addAll(hs1);
                                    }
                                } catch (NumberFormatException e1) {

                                }
                                line = reader.readLine();
                            }
                            reader.close();

                        } catch (IOException ex) {
                            System.err.println("ERROR!");
                        } */
                    }

                }
                casomira.setText(Integer.toString(cas[0]));

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                skorel.setText(Integer.toString(skore));

                if (i < 60 / finalKolikrat) i++;
                else {
                    i = 0;
                    novyButton();
                }
            }
        }.start();
    }
}