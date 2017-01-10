package ua.java.fx.interfaces.impls;

import ua.java.fx.interfaces.AddressBook;
import ua.java.fx.objects.Person;

import java.util.ArrayList;

public class CollectionAddressBook implements AddressBook{

    private ArrayList<Person> personList = new ArrayList<>();

    @Override
    public void add(Person person) {

    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }
}
