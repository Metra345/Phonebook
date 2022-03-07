package vsu.ru.cs.phonebook.service;

import vsu.ru.cs.phonebook.model.Abonent;

public final class Filter {
    public static boolean check(Abonent abonent, Abonent pattern) {
        String s1;
        String s2;
        s1 = abonent.getFirstName();
        s2 = pattern.getFirstName();
        boolean res1 = s2 == null || s2.equals("") || s1.contains(s2);

        s1 = abonent.getLastName();
        s2 = pattern.getLastName();
        boolean res2 = s2 == null || s2.equals("") || s1.contains(s2);

        s1 = abonent.getPhoneNumber(1);
        s2 = pattern.getPhoneNumber(1);
        boolean res3 = s2 == null || s2.equals("") || s1.contains(s2);

        s1 = abonent.getPhoneNumber(2);
        boolean res4 = s2 == null || s2.equals("") || s1.contains(s2);

        s1 = abonent.getPhoneNumber(3);
        boolean res5 = s2 == null || s2.equals("") || s1.contains(s2);
        return res1 && res2 && (res3 || res4 || res5);
    }
}
