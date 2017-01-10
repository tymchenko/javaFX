package ua.java.fx.interfaces;

import ua.java.fx.objects.Person;

public interface AddressBook {

    void add(Person person);

    void update(Person person);

    void delete(Person person);
}
