package types;

import types.enums.EnterpriseType;

import java.util.Objects;

public final class LegalEntity extends Client {
    private final EnterpriseType enterpriseType;
    private final String nominalOwner;

    public LegalEntity(
            String clientType,
            String name,
            String INN,
            String BIC,
            String address,
            EnterpriseType enterpriseType,
            String nominalOwner
    ) {
        super(clientType, name, INN, BIC, address);
        this.enterpriseType = enterpriseType;
        this.nominalOwner = nominalOwner;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public String getNominalOwner() {
        return nominalOwner;
    }

    @Override
    public String toString() {
        return "LegalEntity { " +
                super.toString() + ", " +
                "enterpriseType=" + enterpriseType +
                ", nominalOwner='" + nominalOwner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalEntity legalEntity = (LegalEntity) o;
        return enterpriseType.equals(legalEntity.getEnterpriseType())
                && nominalOwner.equals(legalEntity.getNominalOwner())
                && isEqualByCommonFields(legalEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getClientType(),
                getName(),
                getINN(),
                getBIC(),
                getAddress(),
                getEnterpriseType(),
                getNominalOwner()
        );
    }
}
