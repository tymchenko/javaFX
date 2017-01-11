package ua.java.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.java.fx.objects.Person;

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
        person.setPhone(txtPhone.getText());
        person.setNameSorname(txtNameSoname.getText());
        actionClose(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }
}
