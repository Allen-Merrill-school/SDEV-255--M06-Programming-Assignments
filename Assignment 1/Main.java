import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    //Conncect to database
    public static class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/Staff_Database"; 
        private static final String USER = "root"; 
        private static final String PASSWORD = ""; 
        
        public static Connection connect() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class StaffDAO {
        
        //View staff details by ID
        public static void viewStaff(String id, TextField lastNameField, TextField firstNameField, TextField miField, TextField addressField, TextField cityField, TextField stateField, TextField telephoneField, TextField emailField) {
            String query = "SELECT * FROM Staff WHERE id = ?";
            try (Connection connection = DBConnection.connect();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    lastNameField.setText(rs.getString("lastName"));
                    firstNameField.setText(rs.getString("firstName"));
                    miField.setText(rs.getString("mi"));
                    addressField.setText(rs.getString("address"));
                    cityField.setText(rs.getString("city"));
                    stateField.setText(rs.getString("state"));
                    telephoneField.setText(rs.getString("telephone"));
                    emailField.setText(rs.getString("email"));
                } else {
                    System.out.println("Staff member not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Insert new staff record
        public static void insertStaff(String id, String lastName, String firstName, String mi, String address, String city, String state, String telephone, String email) {
            String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = DBConnection.connect();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                
                stmt.setString(1, id);
                stmt.setString(2, lastName);
                stmt.setString(3, firstName);
                stmt.setString(4, mi);
                stmt.setString(5, address);
                stmt.setString(6, city);
                stmt.setString(7, state);
                stmt.setString(8, telephone);
                stmt.setString(9, email);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Staff record inserted successfully.");
                } else {
                    System.out.println("Failed to insert staff record.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Update existing staff record
        public static void updateStaff(String id, String lastName, String firstName, String mi, String address, String city, String state, String telephone, String email) {
            String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
            try (Connection connection = DBConnection.connect();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                
                stmt.setString(1, lastName);
                stmt.setString(2, firstName);
                stmt.setString(3, mi);
                stmt.setString(4, address);
                stmt.setString(5, city);
                stmt.setString(6, state);
                stmt.setString(7, telephone);
                stmt.setString(8, email);
                stmt.setString(9, id);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Staff record updated successfully.");
                } else {
                    System.out.println("Failed to update staff record.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        //GUI elements
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField miField = new TextField();
        miField.setPromptText("MI");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField stateField = new TextField();
        stateField.setPromptText("State");

        TextField telephoneField = new TextField();
        telephoneField.setPromptText("Telephone");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        //Buttons for actions
        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
            StaffDAO.viewStaff(idField.getText(), lastNameField, firstNameField, miField, addressField, cityField, stateField, telephoneField, emailField);
        });

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> {
            StaffDAO.insertStaff(idField.getText(), lastNameField.getText(), firstNameField.getText(), miField.getText(), addressField.getText(), cityField.getText(), stateField.getText(), telephoneField.getText(), emailField.getText());
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            StaffDAO.updateStaff(idField.getText(), lastNameField.getText(), firstNameField.getText(), miField.getText(), addressField.getText(), cityField.getText(), stateField.getText(), telephoneField.getText(), emailField.getText());
        });

        //Layout to make it fit
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        
        layout.add(new Label("ID:"), 0, 0);
        layout.add(idField, 1, 0);
        layout.add(new Label("Last Name:"), 0, 1);
        layout.add(lastNameField, 1, 1);
        layout.add(new Label("First Name:"), 0, 2);
        layout.add(firstNameField, 1, 2);
        layout.add(new Label("MI:"), 0, 3);
        layout.add(miField, 1, 3);
        layout.add(new Label("Address:"), 0, 4);
        layout.add(addressField, 1, 4);
        layout.add(new Label("City:"), 0, 5);
        layout.add(cityField, 1, 5);
        layout.add(new Label("State:"), 0, 6);
        layout.add(stateField, 1, 6);
        layout.add(new Label("Telephone:"), 0, 7);
        layout.add(telephoneField, 1, 7);
        layout.add(new Label("Email:"), 0, 8);
        layout.add(emailField, 1, 8);
        layout.add(viewButton, 0, 9);
        layout.add(insertButton, 1, 9);
        layout.add(updateButton, 2, 9);

        //Set scene and stage
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setTitle("Staff Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
