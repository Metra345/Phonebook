package vsu.ru.cs.phonebook;

import vsu.ru.cs.phonebook.service.ConsoleClient;

public class Main {

    public static void main(String[] args) {
        ConsoleClient cc = ConsoleClient.getClient();
        cc.run();
    }
}
