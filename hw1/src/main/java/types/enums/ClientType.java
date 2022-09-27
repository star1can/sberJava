package types.enums;

import types.Client;
import types.Holding;
import types.Individual;
import types.LegalEntity;

import java.util.Arrays;
import java.util.HashMap;

public enum ClientType {
    INDIVIDUAL {
        @Override
        public Client createClient(HashMap<String, Object> fields) {
            return new Individual(
                    String.valueOf(fields.get("clientType")),
                    String.valueOf(fields.get("name")),
                    String.valueOf(fields.get("INN")),
                    String.valueOf(fields.get("BIC")),
                    String.valueOf(fields.get("address")),
                    String.valueOf(fields.get("OGRN")),
                    String.valueOf(fields.get("registerDate"))
            );
        }
    }, LEGAL_ENTITY {
        @Override
        public Client createClient(HashMap<String, Object> fields) {
            return new LegalEntity(
                    String.valueOf(fields.get("clientType")),
                    String.valueOf(fields.get("name")),
                    String.valueOf(fields.get("INN")),
                    String.valueOf(fields.get("BIC")),
                    String.valueOf(fields.get("address")),
                    EnterpriseType.valueOf(String.valueOf(fields.get("enterpriseType"))),
                    String.valueOf(fields.get("nominalOwner"))
            );
        }
    }, HOLDING {
        @Override
        public Client createClient(HashMap<String, Object> fields) {
            Object[] objectArray = (Object[]) fields.get("subCompanies");
            String[] subCompanies = Arrays.copyOf(objectArray, objectArray.length, String[].class);

            return new Holding(
                    String.valueOf(fields.get("clientType")),
                    String.valueOf(fields.get("name")),
                    String.valueOf(fields.get("INN")),
                    String.valueOf(fields.get("BIC")),
                    String.valueOf(fields.get("address")),
                    subCompanies);
        }
    };

    public abstract Client createClient(HashMap<String, Object> fields);
}
