package list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import manufacturers.Producer;
import souvenirs.Souvenir;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"producerList"})
public class SouvenirList implements GenericList<Souvenir> {
    List<Souvenir> souvenirList = new ArrayList<>();

    public void add(Souvenir souvenir) {
        souvenirList.add(souvenir);
    }

    @JsonProperty("souvenirs")
    public List<Souvenir> getAll() {
        return souvenirList;
    }

    public Souvenir getById(int id) {
        return souvenirList.stream().filter(souvenir -> souvenir.getSouvenirId() == id).findFirst().orElse(null);
    }

    @Override
    public int size() {
        return souvenirList.size();
    }

    public boolean updateById(int souvenirId, Souvenir souvenir) {
        if (getById(souvenirId) == null) return false;
        souvenirList.set(souvenirList.indexOf(getById(souvenirId)), souvenir);
        souvenir.setSouvenirId(souvenirId);
        return true;
    }

    public boolean deleteById(int id) {
        return souvenirList.remove(getById(id));
    }

    public List<Souvenir> getSouvenirByProducer(Producer producer) {
        return getAll().stream().filter(souvenir -> souvenir.getSouvenirProducer().equals(producer)).toList();
    }

    public boolean deleteSouvenirByProducer(Producer producer) {
        return getAll().removeIf(souvenir -> souvenir.getSouvenirProducer().getProducerName().equals(producer.getProducerName()));
    }
}
