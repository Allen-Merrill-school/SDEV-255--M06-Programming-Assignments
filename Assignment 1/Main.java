import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Create my grid
        GridPane grid = new GridPane();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.setStyle("-fx-padding: 10;"); 

        //Load my photos
        Image image1 = new Image("https://assets1.ignimgs.com/2018/09/04/ps4spider-man-blogroll-01-1536034979782_160w.jpg?width=1280");
        Image image2 = new Image("https://i0.wp.com/comicbookdispatch.com/wp-content/uploads/2024/10/SMBLKSUITBL2024004_Preview_page_1.jpeg?resize=1375%2C768&ssl=1");
        Image image3 = new Image("https://news.uoregon.edu/sites/default/files/styles/custom_xl/public/fever_beyond_amazing_photo_3.png?itok=J8XZtFow");
        Image image4 = new Image("https://www.citypng.com/public/uploads/preview/hd-spider-red-logo-png-701751694782336t1a0dnnwu8.png");

        //Create images with good sizing
        ImageView imageView1 = createImageView(image1);
        ImageView imageView2 = createImageView(image2);
        ImageView imageView3 = createImageView(image3);
        ImageView imageView4 = createImageView(image4);

        //Add the images to my gird
        grid.add(imageView1, 0, 0);
        grid.add(imageView2, 1, 0);
        grid.add(imageView3, 0, 1);
        grid.add(imageView4, 1, 1);

        //Create my scene
        Scene scene = new Scene(grid, 450, 450);

        //Set stage
        primaryStage.setTitle("Image Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Helper method so photots are resized to fit
    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
