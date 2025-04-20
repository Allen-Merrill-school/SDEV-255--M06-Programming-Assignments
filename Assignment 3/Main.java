import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Text text = new Text("Show Colors");

    private Slider redSlider = new Slider(0, 255, 0);
    private Slider greenSlider = new Slider(0, 255, 0);
    private Slider blueSlider = new Slider(0, 255, 0);
    private Slider opacitySlider = new Slider(0, 1, 1);

    @Override
    public void start(Stage primaryStage) {
        text.setStyle("-fx-font-size: 36px;");
        updateColor();

        GridPane slidersPane = new GridPane();
        slidersPane.setPadding(new Insets(20));
        slidersPane.setHgap(10);
        slidersPane.setVgap(10);

        slidersPane.addRow(0, new Label("Red"), redSlider);
        slidersPane.addRow(1, new Label("Green"), greenSlider);
        slidersPane.addRow(2, new Label("Blue"), blueSlider);
        slidersPane.addRow(3, new Label("Opacity"), opacitySlider);

        // Set slider properties
        setupSlider(redSlider);
        setupSlider(greenSlider);
        setupSlider(blueSlider);
        opacitySlider.setShowTickLabels(true);
        opacitySlider.setShowTickMarks(true);
        opacitySlider.setBlockIncrement(0.1);

        // Listeners to update the color
        ChangeListener<Number> sliderListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> updateColor();
        redSlider.valueProperty().addListener(sliderListener);
        greenSlider.valueProperty().addListener(sliderListener);
        blueSlider.valueProperty().addListener(sliderListener);
        opacitySlider.valueProperty().addListener(sliderListener);

        BorderPane root = new BorderPane();
        root.setCenter(text);
        root.setBottom(slidersPane);
        BorderPane.setMargin(slidersPane, new Insets(20));

        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Color Slider Text");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateColor() {
        int red = (int) redSlider.getValue();
        int green = (int) greenSlider.getValue();
        int blue = (int) blueSlider.getValue();
        double opacity = opacitySlider.getValue();
        Color color = Color.rgb(red, green, blue, opacity);
        text.setFill(color);
    }

    private void setupSlider(Slider slider) {
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
