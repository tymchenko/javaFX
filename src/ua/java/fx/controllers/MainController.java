package ua.java.fx.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.java.fx.interfaces.impls.CollectionAddressBook;
import ua.java.fx.objects.Person;

import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDel;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView tableAddressBook;

    @FXML
    private TableColumn <Person, String> columnNameSorname;

    @FXML
    private TableColumn <Person, String> columnPhone;

    @FXML
    private Label lableCount;

    @FXML
    private void initialize(){

        tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnNameSorname.setCellValueFactory(new PropertyValueFactory<Person, String>("nameSorname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });

        addressBookImpl.fillTestData();
        tableAddressBook.setItems(addressBookImpl.getPersonList());
    }

    private void updateCountLabel() {
        lableCount.setText("Кількість записів: " + addressBookImpl.getPersonList().size());
    }

    public void showDialog(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        Person selectedPerson = (Person) tableAddressBook.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "btnAdd":
                System.out.println("add " + selectedPerson);
                break;

            case "btnEdit":
                System.out.println("edit " + selectedPerson);
                break;

            case "btnDel":
                System.out.println("delete " + selectedPerson);
                break;
        }

        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/edit.fxml"));
            stage.setTitle("Редагування запису");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        }catch(IOException e){
            e.printStackTrace();

        }
    }
}
