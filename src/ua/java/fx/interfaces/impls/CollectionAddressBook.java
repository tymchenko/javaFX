package ua.java.fx.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.java.fx.interfaces.AddressBook;
import ua.java.fx.objects.Person;

public class CollectionAddressBook implements AddressBook{

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Override
    public void add(Person person) {
        personList.add(person);
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void fillTestData(){
        personList.add(new Person("Іван Тимченко", "0000000000"));
        personList.add(new Person("Анна Тимченко", "1111111111"));
        personList.add(new Person("Іван Іваненко", "2222222222"));
        personList.add(new Person("Петро Петренко", "3333333333"));
        personList.add(new Person("Василь Василенко", "4444444444"));
        personList.add(new Person("Марія Іваненко", "5555555555"));
        personList.add(new Person("Наталя Петренко", "6666666666"));
        personList.add(new Person("Людмила Василенко", "7777777777"));
        personList.add(new Person("Андрій Андрійчук", "8888888888"));
        personList.add(new Person("Джон Оруелл", "9999999999"));
    }
}