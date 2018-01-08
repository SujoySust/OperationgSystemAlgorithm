package look;

import static java.lang.Math.abs;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LOOK extends Application {

    GridPane grid = new GridPane();
    Label label[] = new Label[10];
    TextField tf[] = new TextField[10];

    static int n, head;
    static int ara[] = new int[200],dup_ara[]= new int[200];
    Canvas canvas = new Canvas(800, 740);

    StackPane root = new StackPane();

    Scene scene = new Scene(root, 800, 750);
    GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {

        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.setVgap(5);
        grid.setHgap(10);

        label[0] = new Label("NUMBER OF QUERIES : ");
        label[1] = new Label("POSITIONS OF SECTORS : ");
        label[2] = new Label("POSITIONS OF HEAD : ");

        tf[0] = new TextField();
        tf[1] = new TextField();
        tf[2] = new TextField();

        ChoiceBox<String> choice = new ChoiceBox();
        choice.getItems().addAll("LEFT", "RIGHT");
        choice.setValue("LEFT");

        ChoiceBox<String> choice_algo = new ChoiceBox();
        choice_algo.getItems().addAll("LOOK", "C-LOOK", "Shortest Seek Time", "SCAN", "C-SCAN", "FCFS");
        choice_algo.setValue("LOOK");

        Button btn = new Button();
        btn.setText("GO");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {

                    n = Integer.parseInt(tf[0].getText());
                    System.out.println(" n = " + n);
                    String str = tf[1].getText();
                    System.out.println(" str = " + str);
                    head = Integer.parseInt(tf[2].getText());
                    System.out.println(" head = " + head);
                    int l = str.length();

                    String st = "";
                    int k = 1;
                    for (int i = 0; i < l; i++) {
                        char c = str.charAt(i);
                        if (c >= '0' && c <= '9') {
                            st = st + c;
                        }
                        if (c == ' ' || i == l - 1) {
                            ara[k] = Integer.parseInt(st);
                            st = "";
                            k++;

                        }

                    }
                    ara[0] = head;
                    for(int i=0;i<=n;i++){
                        dup_ara[i]=ara[i];
                        System.out.println(i+" => "+ara[i]);
                    }
                    String s = choice_algo.getValue();
                    if (s == "FCFS") {
                    } else {
                        for (int i = 0; i <= n; i++) {
                            for (int j = i + 1; j <= n; j++) {
                                if (ara[i] > ara[j]) {
                                    int t = ara[i];
                                    ara[i] = ara[j];
                                    ara[j] = t;
                                }
                            }
                        }
                    }

                    str = choice.getValue();

                    boolean b;
                    if (str == "LEFT") {
                        b = true;
                    } else {
                        b = false;
                    }

                    for (int i = 0; i < k; i++) {
                        System.out.println("-> " + ara[i]);
                    }
                    
                    LOOK ob = new LOOK();
                    if (s == "LOOK") {
                        ob.LOOK(b);
                    } else if (s == "C-LOOK") {
                        ob.C_LOOK(b);
                    } else if (s == "Shortest Seek Time") {
                        ob.SST(b);
                    } else if (s == "SCAN") {
                        ob.SCAN(b);
                    } else if (s == "C-SCAN") {
                        ob.C_SCAN(b);
                    } else if (s == "FCFS") {
                        ob.FCFS();
                    }

                } catch (Exception ex) {
                    System.out.println("QQQQQQQQQQQQQQQQQQQ");
                }
            }
        });

        grid.getChildren().addAll(label[0], label[1], label[2], choice, choice_algo, btn, tf[0], tf[1], tf[2]);

        GridPane.setConstraints(label[0], 1, 3);
        GridPane.setConstraints(label[1], 1, 4);
        GridPane.setConstraints(label[2], 1, 5);
        GridPane.setConstraints(tf[0], 2, 3);
        GridPane.setConstraints(tf[1], 2, 4);
        GridPane.setConstraints(tf[2], 2, 5);
        GridPane.setConstraints(choice, 1, 6);
        GridPane.setConstraints(choice_algo, 1, 7);
        GridPane.setConstraints(btn, 2, 6);

        Scene scene = new Scene(grid, 300, 250);

        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void LOOK(boolean bool) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            int k = 0;
            for (int i = 0; i <= n; i++) {
                if (ara[i] == head) {
                    k = i;
                }
            }
            int b = 10;
            int z = 500 / (n + 1);
            int x = 32;
            if (bool == true) {
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);

                }
                boolean b1 = true;
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    if (b1 == true) {
                        gc.lineTo(ara[0] * 4, x);
                    } else {
                        gc.lineTo(ara[i] * 4, x);
                    }
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);
                    b1 = false;

                }
            } else {
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);

                }
                boolean b1 = true;
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    if (b1 == true) {
                        gc.lineTo(ara[n] * 4, x);
                    } else {
                        gc.lineTo(ara[i] * 4, x);
                    }
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);
                    b1 = false;
                    // System.out.println("=> "+ara[i-1]);

                }
            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();

            draw(gc);
            
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (int i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("LOOK - Algo");
        window.setScene(scene);
        window.show();

    }

    public void C_LOOK(boolean bool) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            int k = 0;
            for (int i = 0; i <= n; i++) {
                if (ara[i] == head) {
                    k = i;
                }
            }
            int b = 10;
            int z = 500 / (n + 1);
            int x = 32;
            if (bool == true) {
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);

                }
                gc.strokeOval(ara[n] * 4 - 5, x - 5, 10, 10);
                String s = "" + ara[n];
                gc.fillText(s, ara[n] * 4 + 20, x - 15, 15);

                boolean b1 = true;
                for (int i = n; i > k; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);
                    b1 = false;

                }
            } else {
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);

                }
                gc.strokeOval(ara[0] * 4 - 5, x - 5, 10, 10);
                String s = "" + ara[0];
                gc.fillText(s, ara[0] * 4 + 20, x - 15, 15);
                boolean b1 = true;
                for (int i = 0; i < k; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);
                    b1 = false;
                    // System.out.println("=> "+ara[i-1]);

                }
            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (int i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("C-LOOK - Algo");
        window.setScene(scene);
        window.show();

    }

    public void SST(boolean bool) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            int k = 0;
            for (int i = 0; i <= n; i++) {
                if (ara[i] == head) {
                    k = i;
                }
            }
            int l = k - 1, r = k + 1, num = n, i;
            int b = 10;
            int z = 500 / (n + 1);
            int x = 32;
            if (bool == true) {
                while (num > 0) {
                    gc.beginPath();
                    gc.lineTo(ara[k] * 4, x);
                    gc.stroke();
                    x = x + z;
                    if (l >= 0 && r <= n) {
                        if ((ara[k] - ara[l]) <= (ara[r] - ara[k])) {
                            i = l;
                            l--;
                        } else {
                            i = r;
                            r++;
                        }
                    } else if (l < 0) {
                        i = r;
                        r++;
                    } else {
                        i = l;
                        l--;
                    }

                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i];
                    gc.fillText(s, ara[i] * 4 + 20, x - 15, 15);
                    k = i;
                    num--;
                }

            } else {
                while (num > 0) {
                    gc.beginPath();
                    gc.lineTo(ara[k] * 4, x);
                    gc.stroke();
                    x = x + z;
                    if (l >= 0 && r <= n) {
                        if ((ara[k] - ara[l]) < (ara[r] - ara[k])) {
                            i = l;
                            l--;
                        } else {
                            i = r;
                            r++;
                        }
                    } else if (l < 0) {
                        i = r;
                        r++;
                    } else {
                        i = l;
                        l--;
                    }

                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i];
                    gc.fillText(s, ara[i] * 4 + 20, x - 15, 15);
                    k = i;
                    num--;
                }

            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("LOOK - Algo");
        window.setScene(scene);
        window.show();

    }

    public void SCAN(boolean bool) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            int k = 0;
            for (int i = 0; i <= n; i++) {
                if (ara[i] == head) {
                    k = i;
                }
            }
            int b = 10;
            int z = 500 / (n + 1);
            int x = 32;
            if (bool == true) {
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);

                }
                gc.beginPath();
                gc.lineTo(ara[0] * 4, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(0, x);
                gc.stroke();

                boolean b1 = true;
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    if (b1 == true) {
                        gc.lineTo(0, x);
                    } else {
                        gc.lineTo(ara[i] * 4, x);
                    }
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);
                    b1 = false;

                }
            } else {
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);

                }

                gc.beginPath();
                gc.lineTo(ara[n] * 4, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(800, x);
                gc.stroke();
                boolean b1 = true;
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    if (b1 == true) {
                        gc.lineTo(800, x);
                    } else {
                        gc.lineTo(ara[i] * 4, x);
                    }
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);
                    b1 = false;
                    // System.out.println("=> "+ara[i-1]);

                }
            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (int i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("SCAN - Algo");
        window.setScene(scene);
        window.show();

    }

    public void C_SCAN(boolean bool) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            int k = 0;
            for (int i = 0; i <= n; i++) {
                if (ara[i] == head) {
                    k = i;
                }
            }
            int b = 10;
            int z = 500 / (n + 2);
            int x = 32;
            if (bool == true) {
                for (int i = k; i > 0; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);

                }

                gc.beginPath();
                gc.lineTo(ara[0] * 4, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(0, x);
                gc.stroke();
                gc.beginPath();
                gc.lineTo(800, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(ara[n] * 4, x);
                gc.stroke();

                gc.strokeOval(ara[n] * 4 - 5, x - 5, 10, 10);
                String s = "" + ara[n];
                gc.fillText(s, ara[n] * 4 + 20, x - 15, 15);

                boolean b1 = true;
                for (int i = n; i > k; i--) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i - 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i - 1] * 4 - 5, x - 5, 10, 10);
                    s = "" + ara[i - 1];
                    gc.fillText(s, ara[i - 1] * 4 + 20, x - 15, 15);
                    b1 = false;

                }
            } else {
                for (int i = k; i < n; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    String s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);

                }

                gc.beginPath();
                gc.lineTo(ara[n] * 4, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(800, x);
                gc.stroke();
                gc.beginPath();
                gc.lineTo(0, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(ara[0] * 4, x);
                gc.stroke();

                gc.strokeOval(ara[0] * 4 - 5, x - 5, 10, 10);
                String s = "" + ara[0];
                gc.fillText(s, ara[0] * 4 + 20, x - 15, 15);

                boolean b1 = true;
                for (int i = 0; i < k; i++) {
                    gc.beginPath();
                    gc.lineTo(ara[i] * 4, x);
                    gc.stroke();
                    x = x + z;
                    gc.lineTo(ara[i + 1] * 4, x);
                    gc.stroke();
                    gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                    s = "" + ara[i + 1];
                    gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);
                    b1 = false;
                    // System.out.println("=> "+ara[i-1]);

                }
            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (int i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("C-SCAN - Algo");
        window.setScene(scene);
        window.show();

    }

    public void FCFS() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {

            gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);

            int k = 0;
            int b = 10;
            int z = 500 / (n + 1);
            int x = 32;

            for (int i = k; i < n; i++) {
                gc.beginPath();
                gc.lineTo(ara[i] * 4, x);
                gc.stroke();
                x = x + z;
                gc.lineTo(ara[i + 1] * 4, x);
                gc.stroke();
                gc.strokeOval(ara[i + 1] * 4 - 5, x - 5, 10, 10);
                String s = "" + ara[i + 1];
                gc.fillText(s, ara[i + 1] * 4 + 20, x - 15, 15);
            }

            gc.beginPath();
            gc.lineTo(0, 30);
            gc.stroke();

            gc.lineTo(800, 30);
            gc.stroke();
            gc.beginPath();
            gc.lineTo(400, 30);
            gc.stroke();

            gc.lineTo(400, 540);
            gc.stroke();
            int f = 0;
            for (int i = 0; i < 10; i++) {
                String st = "";
                st = st + i * 20;
                gc.fillText(st, f, 25, 15);
                f = f + 79;

            }
            gc.fillText("200", 785, 25, 15);
        } catch (Exception e) {

        }

        root.getChildren().add(canvas);

        window.setTitle("FCFS - Algo");
        window.setScene(scene);
        window.show();

    }

    public void draw(GraphicsContext gc) {
//        gc.lineTo(0, 540);
//        gc.stroke();
//
//        gc.lineTo(800, 540);
//        gc.stroke();

        String s1 = "ALGORITHM", s2="TOTAL SEEK TIME";
        gc.fillText(s1,450,555, 60);
        gc.fillText(s2,600,555, 90);
        
        
        s1="FCFS";
        gc.fillText(s1,450,575, 60);
        s1="SHORTEST SEEK TIME";
        gc.fillText(s1,450,595, 160);
        s1="LOOK";
        gc.fillText(s1,450,615, 60);
        s1="C-LOOK";
        gc.fillText(s1,450,635, 60);
        s1="SCAN";
        gc.fillText(s1,450,655, 60);
        s1="C-SACN";
        gc.fillText(s1,450,675, 60);
        
        int m = Seek_FCFS();
        
//        s2=""+seek[0];
//        gc.fillText(s2,600,555, 90);
//        s2=""+seek[1];
//        gc.fillText(s2,600,555, 90);
//        s2=""+seek[2];
//        gc.fillText(s2,600,555, 90);
//        s2=""+seek[3];
//        gc.fillText(s2,600,555, 90);
//        s2=""+seek[4];
//        gc.fillText(s2,600,555, 90);
//        s2=""+seek[5];
//        gc.fillText(s2,600,555, 90);
        
        
        
    }

    public int Seek_FCFS(){
        int haha[]= new int[200];
        
        int xx=0,k=0;
        for(int i=0;i<=n;i++){
            System.out.println("==>"+dup_ara[i]);
            haha[i]=dup_ara[i];
        }
       
        for(int i=n;i>0;i--)
        {
            k=haha[i]-haha[i-1];
            System.out.println("############"+k);
            xx=xx+abs(k);
        }
        System.out.println("bjsaaaaaaaaa   "+xx);  
        return xx;
    }
}
