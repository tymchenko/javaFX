package ua.java.fx.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import ua.java.fx.interfaces.impls.CollectionAddressBook;
import ua.java.fx.objects.Person;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();
    private Stage mainStage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDel;

    @FXML
    private CustomTextField txtSearch;

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
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        fillData();
        setupClearButtonField(txtSearch);
        initListners();
        fillData();
        initLoader();
    }

    private void setupClearButtonField(CustomTextField customTextField){
        try{
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fillData() {
        columnNameSorname.setCellValueFactory(new PropertyValueFactory<Person, String>("nameSorname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
    }

    private void initLoader() {

        try{

            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("ua.java.fx.bundles.Locale", new Locale("en")));
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
        lableCount.setText(resourceBundle.getString("count") + ": " + addressBookImpl.getPersonList().size());
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
            editDialogStage.setTitle(resourceBundle.getString("edit_address"));
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
