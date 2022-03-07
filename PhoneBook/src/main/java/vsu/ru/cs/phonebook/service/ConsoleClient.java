package vsu.ru.cs.phonebook.service;

import vsu.ru.cs.phonebook.model.Abonent;
import vsu.ru.cs.phonebook.model.PhoneBook;

import java.util.Scanner;
import java.util.Set;

public class ConsoleClient {
    private final PhoneBook phb = PhoneBook.getBook();

    private static ConsoleClient cc;

    public static ConsoleClient getClient() {
        if (cc == null) cc = new ConsoleClient();
        return cc;
    }

    private void printAbonent(Abonent abonent) {
        System.out.println("Контакт: " + abonent.getFirstName() + " " + abonent.getLastName() + ":");
        System.out.println("  " + abonent.getPhoneNumber(1));
        System.out.println("  " + abonent.getPhoneNumber(2));
        System.out.println("  " + abonent.getPhoneNumber(3));
        System.out.println();
    }

    private void printAbonents(Set<Abonent> abonentSet) {
        int inc = 0;
        for (Abonent abonent :
                abonentSet) {
            inc++;
            System.out.println(inc + ". " + abonent.getFirstName() + " " + abonent.getLastName() + ":");
            System.out.println("  " + abonent.getPhoneNumber(1));
            System.out.println("  " + abonent.getPhoneNumber(2));
            System.out.println("  " + abonent.getPhoneNumber(3));
            System.out.println();
        }
    }

    public void run() {
        boolean close = false;
        while (!close) {
            int choice;
            choice = readNumFromConsole(1, 4, "Неверный пункт меню.", "MainMenu");
            if (choice == 1) search(false);
            if (choice == 2) search(true);
            if (choice == 3) addAbonent();
            if (choice == 4) close = true;
        }
    }

    private void search(boolean request) {
        Abonent pattern;
        if (request) {
            do {
                printMessage("Search");
                pattern = readFilterParams();
            } while (pattern == null);
        } else {
            pattern = new Abonent("", "", "", "", "");
        }
        boolean repeat = true;
        while (repeat) {
            Set<Abonent> filtered = phb.getMatchingAbonents(pattern);
            if (filtered.size() == 0) {
                System.out.println("Контактов не найдено. Нажмите Enter для продолжения...");
                new Scanner(System.in).nextLine();
                return;
            }
            System.out.println("Результаты поиска");
            printAbonents(filtered);
            int choice = readNumFromConsole(0, filtered.size(), "Введён неправильный номер контакта", "AfterSearch", "" + filtered.size());
            if (choice == 0) return;
            repeat = contanctActions(filtered.stream().toList().get(choice - 1));
        }
    }

    private boolean contanctActions(Abonent abonent) {
        while (true) {
            printAbonent(abonent);
            int choice = readNumFromConsole(1, 5, "Введёно неверное действие", "Contact");
            if (choice == 2) redact(abonent);
            if (choice == 3) {
                if (tryDelete(abonent)) return true;
            }
            if (choice == 4) return true;
            if (choice == 5) return false;
        }
    }

    private void redact(Abonent abonent) {
        Abonent replacement;
        do {
            printMessage("Redact");
            replacement = readFullAbonent();
        } while (replacement == null);
        Redactor.redact(abonent, replacement);
        phb.updateAbonent(abonent);
    }

    private boolean tryDelete(Abonent abonent) {
        int choice = readNumFromConsole(1, 2, "Введёно неверное действие", "CheckSure", "Удалить контакт?");
        if (choice == 1) {
            phb.removeAbonent(abonent);
        }
        return choice == 1;
    }

    private void addAbonent() {
        Abonent newAbonent;
        do {
            printMessage("Add");
            newAbonent = readFullAbonent();
        } while (newAbonent == null);
        phb.addAbonent(newAbonent);
        System.out.println("Контакт добавлен: ");
        printAbonent(newAbonent);
    }

    private int readNumFromConsole(int from, int to, String errMessage, String key, String... args) {
        Scanner scn;
        if (to - from < 0)
            throw new IllegalArgumentException("Неправильный промежуток для выбора(" + from + "," + to + ")");
        printMessage(key, args);
        if (to == from) return from;
        int res;
        while (true) {
            try {
                scn = new Scanner(System.in);
                res = scn.nextInt();
                if (res >= from && res <= to) return res;
            } catch (Exception ignored) {
            }
            System.out.println(errMessage + " Нажмите Enter для продолжения...");
            scn = new Scanner(System.in);
            scn.nextLine();
            printMessage(key, args);
        }
    }

    private void printMessage(String key, String... args) {
        if (key.equals("MainMenu")) {
            System.out.println("1. Ручной поиск");
            System.out.println("2. Поиск по параметрам");
            System.out.println("3. Добавить контакт");
            System.out.println("4. Выход.");
            System.out.print("Выберите пункт: ");
        }
        if (key.equals("Search")) {
            System.out.println("Введите данные поиска через \":\" (неиспользуемые поля заполните пробелами)");
            System.out.println("Примеры запросов:");
            System.out.println("Иван : Иванов : 88005553535");
            System.out.println("ван : анов : 53535");
            System.out.println("Иван : : ");
            System.out.println("Поиск:");
        }
        if (key.equals("AfterSearch")) {
            System.out.println("Введите номер контакта(1..." + args[0] + ", 0 - отменить поиск)");
        }
        if (key.equals("Contact")) {
            System.out.println("1. Позвонить");
            System.out.println("2. Редактировать");
            System.out.println("3. Удалить");
            System.out.println("4. К результатам поиска");
            System.out.println("5. В главное меню");
        }
        if (key.equals("CheckSure")) {
            System.out.println(args[0]);
            System.out.println("1. Да");
            System.out.println("2. Нет");
        }
        if (key.equals("Redact"))
            System.out.println("Введите данные для замены через \":\" (неиспользуемые поля заполните пробелами)");
        if (key.equals("Add"))
            System.out.println("Введите данные для нового контакта через \":\" (неиспользуемые поля заполните пробелами)");
        if (key.equals("Redact") || key.equals("Add")) {
            System.out.println("Примеры запросов:");
            System.out.println("Иван : Иванов : 88005553535 : : ");
            System.out.println("ван : анов : : : 53535");
            System.out.println("Иван : : : : ");
            System.out.println("Ввод данных:");
        }
    }

    private Abonent readFilterParams() {
        Scanner scn = new Scanner(System.in);
        String[] words = scn.nextLine().split(":");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].strip();
        }
        //TODO validate number
        if (words.length == 3) return new Abonent(words[0], words[1], words[2], "", "");
        return null;
    }

    private Abonent readFullAbonent() {
        Scanner scn = new Scanner(System.in);
        String[] words = scn.nextLine().split(":");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].strip();
        }
        //TODO validate number
        if (words.length == 5) return new Abonent(words[0], words[1], words[2], words[3], words[4]);
        return null;
    }
}