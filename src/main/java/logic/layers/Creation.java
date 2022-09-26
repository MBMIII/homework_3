package logic.layers;

import list.ProducerList;
import list.SouvenirList;
import manufacturers.Producer;
import souvenirs.Cup;
import souvenirs.Magnet;
import souvenirs.Souvenir;
import writer.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Creation {

    private String choice;
    private BufferedReader reader;
    private SouvenirList souvenirList;
    private ProducerList producerList;
    private Writer<SouvenirList> souvenirListWriter;
    private Writer<ProducerList> producerListWriter;
    private String souvenirPath;
    private String producerPath;
    private String regEx = "[^0-9#]+";

    public Creation(BufferedReader reader, SouvenirList souvenirList, ProducerList producerList, Writer<SouvenirList> souvenirListWriter, Writer<ProducerList> producerListWriter, String souvenirPath, String producerPath) {
        this.reader = reader;
        this.souvenirList = souvenirList;
        this.souvenirListWriter = souvenirListWriter;
        this.producerListWriter = producerListWriter;
        this.souvenirPath = souvenirPath;
        this.producerPath = producerPath;
        this.producerList = producerList;
    }

    public void creationLevel() throws IOException {
        System.out.println("Make your selection");
        System.out.println("1. Souvenir");
        System.out.println("2. Producer");
        String regEx = "[^0-9#]+";
        choice = reader.readLine().replaceAll(regEx, "");
        if (choice.isEmpty()) choice = "0";
        switch (Integer.parseInt(choice)) {
            case 1 -> {
                souvenirList.add(souvenirCreate());
                producerList.add(souvenirList.getAll().get(souvenirList.size() - 1).getSouvenirProducer());
                producerListWriter.write(producerList, producerPath);
                souvenirListWriter.write(souvenirList, souvenirPath);
                System.out.println("Souvenir was created successfully");
            }
            case 2 -> {
                producerList.add(producerCreate());
                producerListWriter.write(producerList, producerPath);
                System.out.println("Producer was created successfully");
            }
            default -> System.out.println("Incorrect choice");
        }
    }

    public Producer producerCreate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Producer producer = new Producer(producerList);
        System.out.println("Enter producer name");
        producer.setProducerName(reader.readLine());
        System.out.println("Enter producer country");
        producer.setProducerCountry(reader.readLine());
        return producer;
    }

    public Souvenir souvenirCreate() throws IOException {
        String choice = "";
        String name;
        String date;
        String price;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            System.out.println("Please select Souvenir type (number)");
            System.out.println("1. Cup");
            System.out.println("2. Magnet");
            choice = reader.readLine().replaceAll(regEx, "");
            if (choice.isEmpty()) System.out.println("Please make your choice");
            else flag = false;
        }
        return switch (Integer.parseInt(choice)) {
            case 1 -> {
                System.out.println("Enter souvenir name");
                name = reader.readLine();
                System.out.println("Enter date of release DD-MM-YYYY");
                date = reader.readLine();
                System.out.println("Enter price");
                price = reader.readLine();
                yield constructSouvenir(new Cup(), name, date, price);
            }
            case 2 -> {
                System.out.println("Enter souvenir name");
                name = reader.readLine();
                System.out.println("Enter date of release DD-MM-YYYY");
                date = reader.readLine();
                System.out.println("Enter price");
                price = reader.readLine();
                yield constructSouvenir(new Magnet(), name, date, price);
            }
            default -> throw new IllegalStateException("Unexpected value: " + Integer.parseInt(choice));
        };
    }

    public Souvenir constructSouvenir(Souvenir souvenir, String name, String date, String price) throws IOException {
        System.out.println("Do you want to create a new producer or you want to select from existing producers?");
        System.out.println("1. Select from existing producers");
        System.out.println("2. Create new");
        choice = reader.readLine();
        return switch (Integer.parseInt(choice)) {
            case 1 -> {
                if (producerList.getAll().size() == 0) {
                    System.out.println("Sorry, no available producers, please create new producer");
                    yield Souvenir.builder(souvenir)
                            .souvenirName(name)
                            .souvenirProducer(producerCreate())
                            .dateOfRelease(date)
                            .price(Integer.parseInt(price))
                            .build();
                }
                System.out.println("Please choice the producer you want and enter producer id");
                producerList.getAll().forEach(System.out::println);
                choice = reader.readLine();
                yield Souvenir.builder(souvenir)
                        .souvenirName(name)
                        .souvenirProducer(producerList.getById(Integer.parseInt(choice)))
                        .dateOfRelease(date)
                        .price(Integer.parseInt(price))
                        .build();
            }
            case 2 -> Souvenir.builder(souvenir)
                    .souvenirName(name)
                    .souvenirProducer(producerCreate())
                    .dateOfRelease(date)
                    .price(Integer.parseInt(price))
                    .build();
            default -> throw new IllegalStateException("Unexpected value: " + Integer.parseInt(choice));
        };
    }
}
