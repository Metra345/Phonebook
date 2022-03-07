package vsu.ru.cs.phonebook.DataBase.persistence;

import vsu.ru.cs.phonebook.model.Abonent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbonentRepository implements Repository<Abonent, Long> {


    //private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    public AbonentRepository() {};

    public AbonentRepository(String DB_URL, String DB_USER, String DB_PASS) {
        ConnectionManager.getInstance(DB_URL, DB_USER, DB_PASS);
    }

    private Extractor<Abonent> extractor = rs -> {
        List<Abonent> abonentList = new ArrayList<>();
        while (rs.next()) {
            final Abonent abonent = new Abonent(
                    rs.getLong("ID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("PHONE1"),
                    rs.getString("PHONE2"),
                    rs.getString("PHONE3")
            );
            abonentList.add(abonent);
        }
        return abonentList;
    };

    @Override
    public List<Abonent> list() {
        return executeSelect(QueryGenerator.getGenerator().generateSelect(), extractor);
    }

    @Override
    public Abonent find(Long id) {
        final List<Abonent> abonentList = executeSelect(String.format("SELECT * FROM PHONE.PHONEBOOK WHERE id = %d", id), extractor);
        return abonentList.size() > 0 ? abonentList.get(0) : null;
    }

    @Override
    public List<Abonent> search(Abonent pattern) {
        return executeSelect(QueryGenerator.getGenerator().generateSearch(pattern), extractor);
    }

    @Override
    public void insert(Abonent pattern) {
        executeUpdate(QueryGenerator.getGenerator().generateInsert(pattern));
    }

    @Override
    public void delete(Long ID) {
        executeUpdate(QueryGenerator.getGenerator().generateDelete(ID));
    }

    @Override
    public void update(Abonent pattern) {
        executeUpdate(QueryGenerator.getGenerator().generateUpdate(pattern));
    }

    private <T> List<T> executeSelect(String query, Extractor<T> extractor) {
        try (
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            return extractor.extract(resultSet);
        } catch (SQLException e) {
            System.out.println("Unable to get data: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    private void executeUpdate(String query) {
        try (
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Unable to get data: " + e.getMessage());
        }
    }
}