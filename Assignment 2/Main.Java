import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    //Database Connection 
    private static final String URL = "jdbc:mysql://localhost:3306/Temp_Database";
    private static final String USER = "root";  
    private static final String PASSWORD = ""; 

    private TextArea resultArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        //UI elements
        Button connectButton = new Button("Connect to Database and Insert");
        connectButton.setOnAction(e -> runInsertComparison());

        VBox root = new VBox(10, connectButton, resultArea);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Batch Update Performance Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Method to insert and compare
    private void runInsertComparison() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            resultArea.clear();
            resultArea.appendText("Connected to database.\n");

            //Clean Temp table before inserting
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("DELETE FROM Temp");
            }

            //Insert without batch
            long nonBatchTime = insertWithoutBatch(connection);
            resultArea.appendText("Time without batch: " + nonBatchTime + " ms\n");

            //Clean again
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("DELETE FROM Temp");
            }

            //Insert with batch
            long batchTime = insertWithBatch(connection);
            resultArea.appendText("Time with batch: " + batchTime + " ms\n");

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.appendText("Error: " + e.getMessage());
        }
    }

    private long insertWithoutBatch(Connection connection) throws SQLException {
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1000; i++) {
                ps.setDouble(1, Math.random());
                ps.setDouble(2, Math.random());
                ps.setDouble(3, Math.random());
                ps.executeUpdate();  
            }
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private long insertWithBatch(Connection connection) throws SQLException {
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1000; i++) {
                ps.setDouble(1, Math.random());
                ps.setDouble(2, Math.random());
                ps.setDouble(3, Math.random());
                ps.addBatch();  
            }
            ps.executeBatch(); 
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
