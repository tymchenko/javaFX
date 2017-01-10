package ua.java.fx.objects;


public class Person {

    private String nameSorname;
    private String phone;

    public Person(String nameSorname, String phone) {
        this.nameSorname = nameSorname;
        this.phone = phone;
    }

    public String getNameSorname() {
        return nameSorname;
    }

    public void setNameSorname(String nameSorname) {
        this.nameSorname = nameSorname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}