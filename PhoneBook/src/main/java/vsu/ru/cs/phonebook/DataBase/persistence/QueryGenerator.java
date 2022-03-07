package vsu.ru.cs.phonebook.DataBase.persistence;

import vsu.ru.cs.phonebook.model.Abonent;

import java.util.ArrayList;
import java.util.List;

public class QueryGenerator {

    public static QueryGenerator getGenerator() {
        return new QueryGenerator();
    }

    /*
    MAYDAY OBJECT
     */
    private final String P1 = "FIRSTNAME";
    private final String P2 = "LASTNAME";
    private final String P3 = "PHONE1";
    private final String P4 = "PHONE2";
    private final String P5 = "PHONE3";

    private final String[] paramNames = new String[]{P1, P2, P3, P4, P5};
    private final String[] params = new String[5];

    List<Integer> indexes;
    int last;

    public String generateSelect() {
        return "SELECT * FROM PHONE.PHONEBOOK";
    }

    public String generateDelete(Long ID) {
        StringBuilder s = new StringBuilder();
        s
                .append("DELETE FROM PHONE.PHONEBOOK WHERE ID = ")
                .append(ID)
                .append(";");
        return s.toString();
    }

    public String generateSearch(Abonent pattern) {
        prepare(pattern);
        if (last == -1) return generateSelect();
        StringBuilder s = new StringBuilder();
        s
                .append("SELECT * FROM PHONE.PHONEBOOK WHERE")
                .append(Assemble(QueryPart.SRCH))
                .append(";");
        return s.toString();
    }

    public String generateInsert(Abonent pattern) {
        prepare(pattern);
        StringBuilder s = new StringBuilder();
        s
                .append("INSERT INTO PHONE.PHONEBOOK (")
                .append(Assemble(QueryPart.INS_LEFT))
                .append(") VALUES (")
                .append(Assemble(QueryPart.INS_RIGHT))
                .append(");");
        return s.toString();
    }

    public String generateUpdate(Abonent pattern) {
        prepare(pattern);
        StringBuilder s = new StringBuilder();
        s
                .append("UPDATE PHONE.PHONEBOOK SET")
                .append(Assemble(QueryPart.UPD)).append(" WHERE ID = ").append(pattern.getID()).append(";");
        return s.toString();
    }

    private void prepare(Abonent pattern) {
        params[0] = pattern.getFirstName();
        params[1] = pattern.getLastName();
        params[2] = pattern.getPhoneNumber(1);
        params[3] = pattern.getPhoneNumber(2);
        params[4] = pattern.getPhoneNumber(3);

        indexes = new ArrayList<>();
        //-1 не было ниодного параметра значит ничего и не надо посылать
        last = -1;

        for (int i = 0; i < params.length; i++) {
            if (params[i].length() > 0) {
                indexes.add(i);
                last = i;
            }
        }
    }

    private StringBuilder Transform(QueryPart type, int num, boolean isLast) {

        String separator;

        switch (type) {
            case SRCH:
                separator = " AND";
                break;
            default:
                separator = " ,";
        }

        StringBuilder s = new StringBuilder(" ");
        switch (type) {
            case SRCH:
                s.append(paramNames[num]).append(" LIKE ").append("'%").append(params[num]).append("%'");
                break;
            case UPD:
                s.append(paramNames[num]).append(" = ").append("'").append(params[num]).append("'");
                break;
            case INS_LEFT:
                s.append(paramNames[num]);
                break;
            case INS_RIGHT:
                s.append("'").append(params[num]).append("'");
                break;

            default:
                throw new RuntimeException("ERROR");
        }
        if (!isLast) s.append(separator);
        return s;
    }

    public StringBuilder Assemble(QueryPart type) {

        StringBuilder s = new StringBuilder();

        for (int index :
                indexes) {
            s.append(Transform(type, index, index == last));
        }

        return s;
    }
}

enum QueryPart {
    SRCH,
    INS_LEFT,
    INS_RIGHT,
    UPD
}
