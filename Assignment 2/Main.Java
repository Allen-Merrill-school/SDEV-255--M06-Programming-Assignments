import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Create the white circle
        Circle circle = new Circle(100, Color.WHITE);

        //Mouse press changes to black
        circle.setOnMousePressed((MouseEvent event) -> {
            circle.setFill(Color.BLACK);
        });

        //Mouse release makes it white
        circle.setOnMouseReleased((MouseEvent event) -> {
            circle.setFill(Color.WHITE);
        });

        //Set up scene
        StackPane root = new StackPane();
        root.getChildren().add(circle);
        Scene scene = new Scene(root, 300, 300);

        //Set up stage
        primaryStage.setTitle("Circle Color Change");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
