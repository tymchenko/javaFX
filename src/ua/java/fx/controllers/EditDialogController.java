package ua.java.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.java.fx.objects.Person;
import ua.java.fx.utils.DialogManager;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDialogController implements Initializable{

    private Person person;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnOk;

    @FXML
    private TextField txtNameSoname;

    @FXML
    private Button btnCancel;

    private ResourceBundle resourceBundle;

    public void setPerson(Person person){
        if(person == null){
            return;
        }
        this.person = person;
        txtNameSoname.setText(person.getNameSorname());
        txtPhone.setText(person.getPhone());
    }

    public Person getPerson(){
        return person;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent){
        if(!checkValues()){
            return;
        }
        person.setPhone(txtPhone.getText());
        person.setNameSorname(txtNameSoname.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if(txtNameSoname.getText().trim().length() == 0 || txtPhone.getText().trim().length() == 0){
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }
}
