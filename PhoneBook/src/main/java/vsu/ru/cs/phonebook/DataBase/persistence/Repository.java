package vsu.ru.cs.phonebook.DataBase.persistence;

import java.util.List;

public interface Repository<T, ID> {
    List<T> list();

    T find(ID ID);

    List<T> search(T pattern);

    void insert(T pattern);

    void delete(ID ID);

    void update(T pattern);
}
