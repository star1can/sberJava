package types;

import java.util.Arrays;
import java.util.Objects;

public final class Individual extends Client {
    private final String OGRN;
    private final String registerDate;

    public Individual(
            String clientType,
            String name,
            String INN,
            String BIC,
            String address,
            String OGRN,
            String registerDate
    ) {
        super(clientType, name, INN, BIC, address);
        this.OGRN = OGRN;
        this.registerDate = registerDate;
    }

    public String getOGRN() {
        return OGRN;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    @Override
    public String toString() {
        return "Individual { " +
                super.toString() + ", " +
                "OGRN='" + OGRN + '\'' +
                ", registerDate='" + registerDate + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual individual = (Individual) o;
        return OGRN.equals(individual.getOGRN())
                && registerDate.equals(individual.getRegisterDate())
                && isEqualByCommonFields(individual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getClientType(),
                getName(),
                getINN(),
                getBIC(),
                getAddress(),
                getOGRN(),
                getRegisterDate()
        );
    }
}
