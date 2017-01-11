package ua.java.fx.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ua.java.fx.interfaces.impls.CollectionAddressBook;
import ua.java.fx.objects.Person;

import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();
    private Stage mainStage;

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

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;

    @FXML
    private void initialize(){

        tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        fillData();
        initListners();

        addressBookImpl.fillTestData();
        tableAddressBook.setItems(addressBookImpl.getPersonList());

        initLoader();
    }

    private void fillData() {
        columnNameSorname.setCellValueFactory(new PropertyValueFactory<Person, String>("nameSorname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
    }

    private void initLoader() {

        try{

            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initListners() {

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });

        tableAddressBook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2){
                    editDialogController.setPerson((Person) tableAddressBook.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });

    }

    private void updateCountLabel() {
        lableCount.setText("Кількість записів: " + addressBookImpl.getPersonList().size());
    }

    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        Person selectedPerson = (Person) tableAddressBook.getSelectionModel().getSelectedItem();

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        editDialogController.setPerson(selectedPerson);

        switch (clickedButton.getId()){
            case "btnAdd":
                editDialogController.setPerson(new Person());
                showDialog();
                addressBookImpl.add(editDialogController.getPerson());
                break;

            case "btnEdit":
                editDialogController.setPerson((Person) tableAddressBook.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDel":
                addressBookImpl.delete((Person) tableAddressBook.getSelectionModel().getSelectedItem());
                break;
        }


    }

    private void showDialog(){

        if(editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редагування запису");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }

        editDialogStage.showAndWait();
    }

    public void setMainStage(Stage mainStage){
        this.mainStage = mainStage;
    }
}
