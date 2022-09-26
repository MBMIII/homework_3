package souvenirs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import manufacturers.Producer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "souvenirType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cup.class, name = "Cup"),
        @JsonSubTypes.Type(value = Magnet.class, name = "Magnet")
})
public abstract class Souvenir {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int souvenirId;

    private String souvenirName;
    private Producer souvenirProducer;
    private String dateOfRelease;
    private int price;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String souvenirType;

    public Souvenir() {
        souvenirId = count.incrementAndGet();
    }

    public String getSouvenirName() {
        return souvenirName;
    }

    public Producer getSouvenirProducer() {
        return souvenirProducer;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public int getPrice() {
        return price;
    }

    public int getSouvenirId() {
        return souvenirId;
    }

    public void setSouvenirId(int souvenirId) {
        this.souvenirId = souvenirId;
    }

    public void setSouvenirType(String souvenirType) {
        this.souvenirType = souvenirType;
    }

    public String getSouvenirType() {
        return souvenirType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return price == souvenir.price && Objects.equals(souvenirName, souvenir.souvenirName) && Objects.equals(souvenirProducer, souvenir.souvenirProducer) && Objects.equals(dateOfRelease, souvenir.dateOfRelease) && Objects.equals(souvenirType, souvenir.souvenirType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(souvenirName, souvenirProducer, dateOfRelease, price, souvenirType);
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "souvenirId=" + souvenirId +
                ", souvenirName='" + souvenirName + '\'' +
                ", souvenirProducer=" + souvenirProducer +
                ", dateOfRelease='" + dateOfRelease + '\'' +
                ", price=" + price +
                '}';
    }

    public static Builder builder(Souvenir souvenir) {
        return new Builder(souvenir);
    }

    public static class Builder {
        private final Souvenir souvenir;

        private Builder(Souvenir souvenir) {
            this.souvenir = souvenir;
        }

        public Builder souvenirName(String souvenirName) {
            souvenir.souvenirName = souvenirName;
            return this;
        }

        public Builder souvenirProducer(Producer souvenirProducer) {
            souvenir.souvenirProducer = souvenirProducer;
            return this;
        }

        public Builder dateOfRelease(String dateOfRelease) {
            souvenir.dateOfRelease = dateOfRelease;
            return this;
        }

        public Builder price(int price) {
            souvenir.price = price;
            return this;
        }

        public Souvenir build() {
            return souvenir;
        }
    }
}
