package logic.layers;

import list.ProducerList;
import list.SouvenirList;
import manufacturers.Producer;
import souvenirs.Souvenir;
import writer.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class Editing {

    private BufferedReader reader;
    private SouvenirList souvenirList;
    private ProducerList producerList;
    private Writer<SouvenirList> souvenirListWriter;
    private Writer<ProducerList> producerListWriter;
    private String souvenirPath;
    private String producerPath;
    private Creation creation;
    private String choice;
    private String regEx = "[^0-9#]+";

    public Editing(Creation creation, BufferedReader reader, SouvenirList souvenirList, ProducerList producerList, Writer<SouvenirList> souvenirListWriter, Writer<ProducerList> producerListWriter, String souvenirPath, String producerPath) {
        this.reader = reader;
        this.souvenirList = souvenirList;
        this.souvenirListWriter = souvenirListWriter;
        this.producerListWriter = producerListWriter;
        this.souvenirPath = souvenirPath;
        this.producerPath = producerPath;
        this.producerList = producerList;
        this.creation = creation;
    }

    public void editingLevel() throws IOException {
        System.out.println("Make your selection");
        System.out.println("1. Souvenir");
        System.out.println("2. Producer");
        choice = reader.readLine().replaceAll(regEx, "");
        switch (Integer.parseInt(choice)) {
            case 1 -> editSouvenir();
            case 2 -> editProducer();
            default -> System.out.println("Sorry incorrect choice");
        }
    }

    public void editSouvenir() throws IOException {
        System.out.println("Please enter souvenirId which you want to edit");
        souvenirList.getAll().forEach(System.out::println);
        choice = reader.readLine().replaceAll(regEx, "");
        if (souvenirList.getById(Integer.parseInt(choice)) != null) {
            souvenirList.updateById(Integer.parseInt(choice), creation.souvenirCreate());
            souvenirListWriter.write(souvenirList, souvenirPath);
            System.out.println("Souvenir was updated successfully");
        }
        System.out.println("Incorrect id");
    }

    public void editProducer() throws IOException {
        System.out.println("Please enter producer id which you want to edit");
        producerList.getAll().forEach(System.out::println);
        choice = reader.readLine().replaceAll(regEx, "");
        if (producerList.getById(Integer.parseInt(choice)) != null) {
            producerList.updateById(Integer.parseInt(choice), creation.producerCreate());
            Optional<Producer> optionalProducer = souvenirList.getAll().stream()
                    .map(Souvenir::getSouvenirProducer)
                    .filter(producer -> producer.getProducerId() == Integer.parseInt(choice))
                    .findAny();
            if (optionalProducer.isPresent()) {
                Producer temp;
                temp = optionalProducer.get();
                temp.setProducerName(producerList.getById(Integer.parseInt(choice)).getProducerName());
                temp.setProducerCountry(producerList.getById(Integer.parseInt(choice)).getProducerCountry());
                producerListWriter.write(producerList, producerPath);
                souvenirListWriter.write(souvenirList, souvenirPath);
                System.out.println("Producer was updated successfully");
            }
        } else {
            System.out.println("Incorrect id");
        }
    }
}
