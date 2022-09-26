package manufacturers;

import list.ProducerList;

import java.util.Objects;

public class Producer {

    private int producerId;
    private String producerName;
    private String producerCountry;

    public Producer() {
    }

    public Producer(ProducerList producerList) {
        producerId = producerList.size() + 1;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerCountry() {
        return producerCountry;
    }

    public void setProducerCountry(String producerCountry) {
        this.producerCountry = producerCountry;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(producerName, producer.producerName) && Objects.equals(producerCountry, producer.producerCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producerName, producerCountry);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + producerId +
                ", producerName='" + producerName + '\'' +
                ", producerCountry='" + producerCountry + '\'' +
                '}';
    }
}
