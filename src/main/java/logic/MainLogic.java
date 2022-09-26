package logic;

import list.ProducerList;
import list.SouvenirList;
import logic.layers.Creation;
import logic.layers.Deleting;
import logic.layers.Editing;
import logic.layers.Printing;
import reader.ReadProducerFromJson;
import reader.ReadSouvenirsFromJson;
import writer.WriteProducersToJson;
import writer.WriteSouvenirToJson;
import writer.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainLogic {

    private SouvenirList souvenirList;
    private ProducerList producerList;
    private Writer<ProducerList> producerListWriter;
    private Writer<SouvenirList> souvenirListWriter;
    private BufferedReader reader;
    private Creation creation;
    private Printing printing;
    private Deleting deleting;
    private Editing editing;

    public MainLogic(String souvenirPath, String producerPath) {
        souvenirList = new ReadSouvenirsFromJson().read(souvenirPath);
        producerList = new ReadProducerFromJson().read(producerPath);
        producerListWriter = new WriteProducersToJson();
        souvenirListWriter = new WriteSouvenirToJson();
        reader = new BufferedReader(new InputStreamReader(System.in));
        creation = new Creation(reader, souvenirList, producerList, souvenirListWriter, producerListWriter, souvenirPath, producerPath);
        printing = new Printing(reader, souvenirList, producerList);
        deleting = new Deleting(reader, souvenirList, producerList, souvenirListWriter, producerListWriter, souvenirPath, producerPath);
        editing = new Editing(creation, reader, souvenirList, producerList, souvenirListWriter, producerListWriter, souvenirPath, producerPath);
    }

    public void start() throws IOException {
        boolean flag = true;
        String choice;
        System.out.println("Hello, what action you would like to perform?");
        while (flag) {
            makeChoiceMainLevel();
            String regEx = "[^0-9#]+";
            choice = reader.readLine().replaceAll(regEx, "");
            if (choice.isEmpty()) choice = "-1";
            switch (Integer.parseInt(choice)) {
                case 1 -> creation.creationLevel();
                case 2 -> editing.editingLevel();
                case 3 -> printing.printLevel();
                case 4 -> deleting.deleteLevel();
                case 0 -> flag = false;
                default -> System.out.println("Incorrect choice");
            }
        }
    }

    public void makeChoiceMainLevel() {
        System.out.println("Make your selection (number)");
        System.out.println("1. Create");
        System.out.println("2. Editing");
        System.out.println("3. Print");
        System.out.println("4. Delete");
        System.out.println("0. Exit");
    }
}
