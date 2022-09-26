package factory;

import souvenirs.Cup;
import souvenirs.Magnet;
import souvenirs.Souvenir;

public class SouvenirFactory {
    public enum SouvenirType {CUP, MAGNET}

    public static Souvenir createSouvenir(SouvenirType souvenirType) {
        return switch (souvenirType) {
            case CUP -> new Cup();
            case MAGNET -> new Magnet();
        };
    }
}
