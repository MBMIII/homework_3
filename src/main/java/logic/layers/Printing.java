package logic.layers;

import list.ProducerList;
import list.SouvenirList;
import manufacturers.Producer;
import souvenirs.Souvenir;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Printing {

    private String choice;
    private BufferedReader reader;
    private SouvenirList souvenirList;
    private ProducerList producerList;
    private String regEx = "[^0-9#]+";


    public Printing(BufferedReader reader, SouvenirList souvenirList, ProducerList producerList) {
        this.reader = reader;
        this.souvenirList = souvenirList;
        this.producerList = producerList;
    }


    public void makeChoicePrintingLevel() {
        System.out.println("1. Print all souvenirs by producer");
        System.out.println("2. Print all souvenirs by country");
        System.out.println("3. Print all souvenirs produced in specific year");
        System.out.println("4. Print all producers where price is lower then...");
        System.out.println("5. Print all producers with produced souvenirs");
        System.out.println("6. Print all producers for souvenir produced in specific year");
        System.out.println("7. Print all souvenirs grouped by year");
    }

    public void printLevel() throws IOException {
        if (souvenirList.size() == 0 && producerList.size() == 0)
            System.out.println("Nothing to print");
        makeChoicePrintingLevel();
        choice = reader.readLine().replaceAll(regEx, "");
        switch (Integer.parseInt(choice)) {
            case 1 -> printSouvenirsByProducer();
            case 2 -> printSouvenirsByCountry();
            case 3 -> printSouvenirsByYear();
            case 4 -> printAllProducersWherePriceIsLower();
            case 5 -> printAllProducersWithProducedSouvenirs();
            case 6 -> printAllProducersForSouvenirProducedInSpecificYear();
            case 7 -> printAllSouvenirsGroupedByYear();
            default -> System.out.println("Incorrect choice");
        }
    }

    private void printAllSouvenirsGroupedByYear() {
        Map<String, List<Souvenir>> souvenirsByYear = souvenirList.getAll().stream().collect(
                Collectors.groupingBy(souvenir -> souvenir.getDateOfRelease().replaceAll(".{6}", "")));
        for (Map.Entry<String, List<Souvenir>> entry : souvenirsByYear.entrySet()) {
            System.out.println("In year: " + entry.getKey() + " was produced " + entry.getValue());
        }
    }

    private void printAllProducersForSouvenirProducedInSpecificYear() throws IOException {
        System.out.println("Please enter souvenir name");
        souvenirList.getAll().stream().map(Souvenir::getSouvenirName).distinct().forEach(System.out::println);
        String souvenirName = reader.readLine();
        List<Souvenir> res = souvenirList.getAll().stream().filter(souvenir -> souvenir.getSouvenirName().equals(souvenirName)).toList();
        System.out.println("Please enter produced year");
        souvenirList.getAll().stream()
                .filter(souvenir -> souvenir.getSouvenirName().equals(souvenirName))
                .forEach(souvenir -> System.out.println(souvenir.getDateOfRelease().replaceAll(".{6}", "")));
        String producedYear = reader.readLine().replaceAll(regEx, "");
        res.stream()
                .filter(souvenir -> souvenir.getDateOfRelease().replaceAll(".{6}", "").equals(producedYear))
                .map(Souvenir::getSouvenirProducer)
                .forEach(System.out::println);
    }

    private void printAllProducersWithProducedSouvenirs() {
        for (Producer producer : producerList.getAll()) {
            System.out.println("Producer : " + producer.getProducerName() + " has produced " + souvenirList.getSouvenirByProducer(producer).size() + " souvenir(s)");
            souvenirList.getAll().stream()
                    .filter(souvenir -> souvenir.getSouvenirProducer().equals(producer))
                    .forEach(System.out::println);
        }
    }

    private void printAllProducersWherePriceIsLower() throws IOException {
        System.out.println("Enter price of product");
        choice = reader.readLine().replaceAll(regEx, "");
        souvenirList.getAll()
                .stream()
                .filter(souvenir -> souvenir.getPrice() <= Integer.parseInt(choice))
                .map(Souvenir::getSouvenirProducer)
                .distinct()
                .forEach(System.out::println);
    }

    private void printSouvenirsByProducer() throws IOException {
        souvenirList.getAll().stream().map(Souvenir::getSouvenirProducer).distinct().map(Producer::getProducerName).forEach(System.out::println);
        System.out.println("Please enter producer name which souvenirs you want to display");
        choice = reader.readLine();
        souvenirList.getAll()
                .stream()
                .filter(souvenir -> souvenir.getSouvenirProducer().getProducerName().equals(choice))
                .forEach(System.out::println);
    }

    private void printSouvenirsByCountry() throws IOException {
        souvenirList.getAll().stream()
                .map(Souvenir::getSouvenirProducer)
                .map(Producer::getProducerCountry)
                .distinct()
                .forEach(System.out::println);
        System.out.println("Please enter producer country which souvenirs you want to display");
        choice = reader.readLine().replaceAll("\\d+", "");
        souvenirList.getAll()
                .stream()
                .filter(souvenir -> souvenir.getSouvenirProducer().getProducerCountry().equals(choice))
                .forEach(System.out::println);
    }

    private void printSouvenirsByYear() throws IOException {
        souvenirList.getAll().stream().map(Souvenir::getSouvenirName).distinct().forEach(System.out::println);
        System.out.println("Enter souvenir name");
        choice = reader.readLine();
        List<Souvenir> list = souvenirList.getAll().stream().filter(souvenir -> souvenir.getSouvenirName().equals(choice)).toList();
        list.stream().map(souvenir -> souvenir.getDateOfRelease().replaceAll(".{6}", "")).distinct().forEach(System.out::println);
        System.out.println("Enter year from list");
        choice = reader.readLine().replaceAll(regEx, "");
        list.stream()
                .filter(souvenir -> souvenir.getDateOfRelease().replaceAll(".{6}", "").equals(choice))
                .forEach(System.out::println);

    }
}
