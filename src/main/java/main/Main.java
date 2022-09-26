package main;

import logic.MainLogic;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String souvenirPath = "./src/main/resources/souvenir list.json";
        String producerPath = "./src/main/resources/producer list.json";
        MainLogic logic = new MainLogic(souvenirPath, producerPath);
        logic.start();
    }
}