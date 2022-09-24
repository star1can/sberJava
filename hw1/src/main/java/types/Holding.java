package types;

import java.util.Arrays;
import java.util.Objects;

public final class Holding extends Client {
    private final String[] subCompanies;

    public Holding(
            String clientType,
            String name,
            String INN,
            String BIC,
            String address,
            String[] subCompanies
    ) {
        super(clientType, name, INN, BIC, address);
        this.subCompanies = subCompanies;
    }

    public String[] getSubCompanies() {
        return subCompanies;
    }

    @Override
    public String toString() {
        return "Holding { " +
                super.toString() + ", " +
                "subCompanies=" + Arrays.toString(subCompanies) +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holding holding = (Holding) o;
        return Arrays.deepEquals(subCompanies, holding.getSubCompanies())
                && isEqualByCommonFields(holding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getClientType(),
                getName(),
                getINN(),
                getBIC(),
                getAddress(),
                Arrays.hashCode(subCompanies)
        );
    }
}
