package vsu.ru.cs.phonebook.model;

//Класс реализующий контакт

public class Abonent implements Comparable {
    private long ID;
    private String firstName;
    private String lastName;
    private final String[] phoneNumbers = new String[3];

    public Abonent(long ID, String firstName, String lastName, String number1, String number2, String number3) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        phoneNumbers[0] = number1;
        phoneNumbers[1] = number2;
        phoneNumbers[2] = number3;
    }

    public Abonent(String firstName, String lastName, String number1, String number2, String number3) {
        this(-1, firstName, lastName, number1, number2, number3);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Abonent another) {
            int res = firstName.compareTo(another.firstName);
            if (res != 0) return res;
            res = lastName.compareTo(another.lastName);
            if (res != 0) return res;
            for (int i = 0; i < phoneNumbers.length; i++) {
                res = phoneNumbers[i].compareTo(another.phoneNumbers[i]);
                if (res != 0) return res;
            }
            return 0;
        }
        return -1;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber(int number) {
        if (number > 0 && number < 4) {
            return phoneNumbers[number - 1];
        }
        return null;
    }

    public boolean trySetPhoneNumber(int number, String phoneNumber) {
        if (number > 0 && number < 4) {
            phoneNumbers[number - 1] = phoneNumber;
            return true;
        }
        return false;
    }

}