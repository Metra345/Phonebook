package vsu.ru.cs.phonebook.model;

import vsu.ru.cs.phonebook.DataBase.persistence.AbonentRepository;
import vsu.ru.cs.phonebook.DataBase.persistence.Repository;

import java.util.HashSet;
import java.util.Set;

//Класс реализующий телефонную книгу

public class PhoneBook {
    private static PhoneBook book;
    private Repository<Abonent, Long> abonentRepository;
    //private Set<Abonent> abonentSet = new TreeSet<>();

    public PhoneBook() {
        abonentRepository = new AbonentRepository();
    }

    public static PhoneBook getBook() {
        if (book == null) book = new PhoneBook();
        return book;
    }

    /*
    public void addAll(List<Abonent> abonents) {
        abonentSet.addAll(abonents);
    }
     */

    public void addAbonent(Abonent abonent) {
        abonentRepository.insert(abonent);
    }

    public void removeAbonent(Abonent abonent) {
        abonentRepository.delete(abonent.getID());
    }

    public void updateAbonent(Abonent abonent) {
        abonentRepository.update(abonent);
    }

    //сет->открываем поток->выбираем абонента по фильтру(проверка по паттрену)->преобразуем в сет
    public Set<Abonent> getMatchingAbonents(Abonent pattern) {
        /*
        return abonentSet.stream()
                .filter(abonent -> Filter.check(abonent, pattern))
                .collect(Collectors.toSet());
         */
        //TODO Переделать?
        /*
        return abonentRepository.search(pattern).stream().collect(Collectors.toSet());
         */
        return new HashSet<>(abonentRepository.search(pattern));
    }

}
