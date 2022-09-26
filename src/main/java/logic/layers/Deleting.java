package logic.layers;

import list.ProducerList;
import list.SouvenirList;
import writer.Writer;

import java.io.BufferedReader;
import java.io.IOException;

public class Deleting {

    private String choice;
    private BufferedReader reader;
    private SouvenirList souvenirList;
    private ProducerList producerList;
    private Writer<SouvenirList> souvenirListWriter;
    private Writer<ProducerList> producerListWriter;
    private String souvenirPath;
    private String producerPath;
    private String regEx = "[^0-9#]+";

    public Deleting(BufferedReader reader, SouvenirList souvenirList, ProducerList producerList, Writer<SouvenirList> souvenirListWriter, Writer<ProducerList> producerListWriter, String souvenirPath, String producerPath) {
        this.reader = reader;
        this.souvenirList = souvenirList;
        this.souvenirListWriter = souvenirListWriter;
        this.producerListWriter = producerListWriter;
        this.souvenirPath = souvenirPath;
        this.producerPath = producerPath;
        this.producerList = producerList;
    }

    public void deleteLevel() throws IOException {
        System.out.println("Make your selection");
        System.out.println("1. Souvenir");
        System.out.println("2. Producer and all produced souvenirs");
        choice = reader.readLine().replaceAll(regEx, "");
        if (choice.isEmpty()) choice = "0";
        switch (Integer.parseInt(choice)) {
            case 1 -> deleteSouvenir();
            case 2 -> deleteProducer();
            default -> System.out.println("Incorrect choice");
        }
    }

    private void deleteSouvenir() throws IOException {
        System.out.println("Enter souvenir id you want to delete ");
        souvenirList.getAll().forEach(souvenir -> System.out.println("id: " + souvenir.getSouvenirId() + ", Name " + souvenir.getSouvenirName()));
        choice = reader.readLine().replaceAll(regEx, "");
        if (souvenirList.deleteById(Integer.parseInt(choice))) {
            souvenirListWriter.write(souvenirList, souvenirPath);
            System.out.println("Souvenir was successfully deleted");
        } else {
            System.out.println("No such id");
        }
    }

    private void deleteProducer() throws IOException {
        System.out.println("Enter producer id you want to delete ");
        producerList.getAll().forEach(producer -> System.out.println("id: " + producer.getProducerId() + ", Name " + producer.getProducerName()));
        choice = reader.readLine().replaceAll(regEx, "");
        if (producerList.getById(Integer.parseInt(choice)) != null) {
            souvenirList.deleteSouvenirByProducer(producerList.getById(Integer.parseInt(choice)));
            producerList.deleteById(Integer.parseInt(choice));
            producerListWriter.write(producerList, producerPath);
            souvenirListWriter.write(souvenirList, souvenirPath);
            System.out.println("Producer was successfully deleted");
        } else {
            System.out.println("No such id");
        }
    }
}
