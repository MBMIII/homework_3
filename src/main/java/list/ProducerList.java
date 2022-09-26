package list;

import com.fasterxml.jackson.annotation.JsonProperty;
import manufacturers.Producer;

import java.util.ArrayList;
import java.util.List;

public class ProducerList implements GenericList<Producer> {
    private List<Producer> producerList;

    public ProducerList() {
        producerList = new ArrayList<>();
    }

    public void add(Producer producer) {
        producerList.add(producer);
        List<Producer> producers = new ArrayList<>(producerList);
        producerList = new ArrayList<>(producers.stream().distinct().toList());
    }

    @JsonProperty("producers")
    public List<Producer> getAll() {
        return producerList;
    }

    public boolean updateById(int producerId, Producer producer) {
        if (getById(producerId) == null) return false;
        producerList.set(producerList.indexOf(getById(producerId)), producer);
        producer.setProducerId(producerId);
        return true;
    }

    public Producer getById(int id) {
        return producerList.stream().filter(producer -> producer.getProducerId() == id).findFirst().orElse(null);
    }

    @Override
    public int size() {
        return producerList.size();
    }

    public boolean deleteById(int id) {
        return producerList.remove(getById(id));
    }
}
