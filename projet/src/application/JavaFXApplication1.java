package application;

import javafx.application.Application;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class JavaFXApplication1 extends Application {
  private TableView<Student> studentTable;
    //LES COMPOSANTS
    private File studentsFile;
    Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label fatherNameLabel = new Label("Father Name:");
        TextField fatherNameField = new TextField();
        Label cnicLabel = new Label("CNIC:");
        TextField cnicField = new TextField();
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        Label contactNoLabel = new Label("Contact No:");
        TextField contactNoField = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Registration");
        
        // Create table columns
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, String> fatherNameColumn = new TableColumn<>("Father Name");
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        TableColumn<Student, String> cnicColumn = new TableColumn<>("CNIC");
        cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Student, String> contactNoColumn = new TableColumn<>("Contact No");
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        // Create table and set columns
        studentTable = new TableView<>();
        studentTable.getColumns().addAll(idColumn, nameColumn, emailColumn, fatherNameColumn, cnicColumn, addressColumn, contactNoColumn);
        
        // Create buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addStudent(idField.getText(), nameField.getText(), emailField.getText(),
                fatherNameField.getText(), cnicField.getText(), addressField.getText(), contactNoField.getText()));
        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> updateStudent(idField.getText(), nameField.getText(), emailField.getText(),
                fatherNameField.getText(), cnicField.getText(), addressField.getText(), contactNoField.getText()));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteStudent());

        Button printButton = new Button("Print Summary");
        printButton.setOnAction(event -> printSummary());

        // Create form layout
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        formPane.addRow(0, idLabel, idField);
        formPane.addRow(1, nameLabel, nameField);
        formPane.addRow(2, emailLabel, emailField);
        formPane.addRow(3, fatherNameLabel, fatherNameField);
        formPane.addRow(4, cnicLabel, cnicField);
        formPane.addRow(5, addressLabel, addressField);
        formPane.addRow(6, contactNoLabel, contactNoField);
        formPane.addRow(7, addButton, updateButton, deleteButton, printButton);

        // Create border pane
        BorderPane root = new BorderPane();
        root.setCenter(studentTable);
        root.setBottom(formPane);

        // Create scene and set to stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

   
    }

    private void addStudent(String id, String name, String email, String fatherName, String cnic, String address, String contactNo) {
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || fatherName.isEmpty() || cnic.isEmpty() || address.isEmpty() || contactNo.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        Student student = new Student(id, name, email, fatherName, cnic, address, contactNo);
        students.add(student);
        clearFormFields();
        updateStudentsFile();
    }

    private void updateStudent(String id, String name, String email, String fatherName, String cnic, String address, String contactNo) {
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || fatherName.isEmpty() || cnic.isEmpty() || address.isEmpty() || contactNo.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        for (Student student : students) {
            if (student.getId().equals(id)) {
                student.setName(name);
                student.setEmail(email);
                student.setFatherName(fatherName);
                student.setCnic(cnic);
                student.setAddress(address);
                student.setContactNo(contactNo);
                clearFormFields();
                updateStudentsFile();
                return;
            }
        }

        showAlert("Error", "Student with ID " + id + " not found.");
    }

    private void deleteStudent() {
        String id = getSelectedStudentId();
        if (id == null) {
            showAlert("Error", "No student selected.");
            return;
        }

        for (Student student : students) {
            if (student.getId().equals(id)) {
                students.remove(student);
                clearFormFields();
                updateStudentsFile();
                return;
            }
        }

        showAlert("Error", "Student with ID " + id + " not found.");
    }

    private void readStudent(String id) {
        if (id.isEmpty()) {
            showAlert("Error", "Please enter student ID.");
            return;
        }

        for (Student student : students) {
            if (student.getId().equals(id)) {
                clearFormFields();
                idField.setText(student.getId());
                nameField.setText(student.getName());
                emailField.setText(student.getEmail());
                fatherNameField.setText(student.getFatherName());
                cnicField.setText(student.getCnic());
                addressField.setText(student.getAddress());
                contactNoField.setText(student.getContactNo());
                return;
            }
        }

        showAlert("Error", "Student with ID " + id + " not found.");
    }

  private void printSummary() {
    if (students.isEmpty()) {
        showAlert("Error", "No students to print.");
        return;
    }

   


    private ObservableList<Student> loadStudentsFromFile(File file) {
        ObservableList<Student> students = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    Student student = new Student(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "An error occurred while loading students from file.");
        }

        return students;
    }

    private void updateStudentsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentsFile))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getEmail() + "," +
                        student.getFatherName() + "," + student.getCnic() + "," + student.getAddress() + "," +
                        student.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "An error occurred while updating students file.");
        }
    }

    private void clearFormFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        fatherNameField.clear();
        cnicField.clear();
        addressField.clear();
        contactNoField.clear();
    }

    private String getSelectedStudentId() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        return (selectedStudent != null) ? selectedStudent.getId() : null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Student {
        private final StringProperty id;
        private final StringProperty name;
        private final StringProperty email;
        private final StringProperty fatherName;
        private final StringProperty cnic;
        private final StringProperty address;
        private final StringProperty contactNo;

        public Student(String id, String name, String email, String fatherName, String cnic, String address, String contactNo) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
            this.fatherName = new SimpleStringProperty(fatherName);
            this.cnic = new SimpleStringProperty(cnic);
            this.address = new SimpleStringProperty(address);
            this.contactNo = new SimpleStringProperty(contactNo);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getFatherName() {
            return fatherName.get();
        }

        public String getCnic() {
            return cnic.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getContactNo() {
            return contactNo.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public void setFatherName(String fatherName) {
            this.fatherName.set(fatherName);
        }

        public void setCnic(String cnic) {
            this.cnic.set(cnic);
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public void setContactNo(String contactNo) {
            this.contactNo.set(contactNo);
        }
    }
}