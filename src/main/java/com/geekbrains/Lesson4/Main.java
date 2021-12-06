package com.geekbrains.Lesson4;

public class Main {
    public static void main(String[] args) {
        User user = User.builder()
                .withName("Ivan")
                .withSurname("Ivanov")
                .withAddress("Cherry street")
                .withEmail("ivanov@mail.ru")
                .withPhone("89117777777")
                .withVkLink("qwerty")
                .withFacebookLink("qwertyuiop")
                .build();
    }
}
