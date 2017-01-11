package ua.java.fx.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import ua.java.fx.utils.DialogManager;

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
    private ObservableList<Person> backupList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnNameSorname.setCellValueFactory(new PropertyValueFactory<Person, String>("nameSorname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

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
        addressBookImpl.fillTestData();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(addressBookImpl.getPersonList());
        tableAddressBook.setItems(addressBookImpl.getPersonList());
    }

    private void initLoader() {

        try{

            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("ua.java.fx.bundles.Locale", new Locale("ua")));
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
                if(!personIsSelected(selectedPerson)){
                    return;
                }

                editDialogController.setPerson(selectedPerson);
                showDialog();
                break;

            case "btnDel":
                if(!personIsSelected(selectedPerson)){
                    return;
                }
                addressBookImpl.delete(selectedPerson);
                break;
        }


    }

    private boolean personIsSelected(Person selectedPerson) {
        if(selectedPerson == null){
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("select_person"));
            return false;
        }
        return true;
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

    public void actionSearch(ActionEvent actionEvent){
        addressBookImpl.getPersonList().clear();

        for(Person person : backupList){
            if(person.getNameSorname().toLowerCase().contains(txtSearch.getText().toLowerCase())
                || person.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase())){
                addressBookImpl.getPersonList().add(person);
            }
        }
    }
}
