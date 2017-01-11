package ua.java.fx.objects;


import javafx.beans.property.SimpleStringProperty;

public class Person {

    private SimpleStringProperty nameSorname = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");

    public Person(){

    }

    public Person(String nameSorname, String phone) {
        this.nameSorname = new SimpleStringProperty(nameSorname);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getNameSorname() {
        return nameSorname.get();
    }

    public void setNameSorname(String nameSorname) {
        this.nameSorname.set(nameSorname);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public SimpleStringProperty nameSornameProperty(){
        return nameSorname;
    }

    public SimpleStringProperty phoneProperty(){
        return phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nameSorname='" + nameSorname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}