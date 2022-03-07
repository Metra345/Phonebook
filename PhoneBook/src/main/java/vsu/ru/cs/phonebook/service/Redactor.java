package vsu.ru.cs.phonebook.service;

import vsu.ru.cs.phonebook.model.Abonent;

public final class Redactor {
    public static void redact(Abonent abonent, Abonent replacement) {
        String str;
        str = replacement.getFirstName();
        if (checkStr(str)) {
            abonent.setFirstName(str);
        }
        str = replacement.getLastName();
        if (checkStr(str)) {
            abonent.setLastName(str);
        }
        for (int i = 1; i < 4; i++) {
            str = replacement.getPhoneNumber(i);
            if (checkStr(str)) {
                if (!abonent.trySetPhoneNumber(i, str)) throw new IndexOutOfBoundsException();
            }
        }
    }

    private static boolean checkStr(String str) {
        return str != null && !str.equals("");
    }
}
