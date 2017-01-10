package ua.java.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.java.fx.objects.Person;

public class EditDialogController {

    private Person person;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnOk;

    @FXML
    private TextField txtNameSoname;

    @FXML
    private Button btnCancel;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setPerson(Person person){
        this.person = person;

        txtNameSoname.setText(person.getNameSorname());
        txtPhone.setText(person.getPhone());
    }
}
