package types;

import types.enums.ClientType;
import util.json.parser.JsonParserImpl;
import util.json.parser.interfaces.JsonParser;

import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Client {
    private final String clientType;

    private final String name;

    private final String INN;

    private final String BIC;

    private final String address;

    public static Client createClientFromJsonFile(String path) {
        try {
            String clientJsonDescription = Files.readString(Paths.get(path));
            return createClientFromJsonString(clientJsonDescription);
        } catch (Exception e) {
            return null;
        }
    }

    public static Client createClientFromJsonString(String jsonString) {
        JsonParser parser = new JsonParserImpl();
        var fields = parser.parse(jsonString);
        ClientType clientType = ClientType.valueOf(String.valueOf(fields.get("clientType")));
        return clientType.createClient(fields);
    }


    public String getName() {
        return name;
    }

    public String getINN() {
        return INN;
    }

    public String getBIC() {
        return BIC;
    }

    public String getAddress() {
        return address;
    }

    public String getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "clientType='" + clientType + '\'' +
                ", name='" + name + '\'' +
                ", INN='" + INN + '\'' +
                ", BIC='" + BIC + '\'' +
                ", address='" + address + '\'';
    }

    protected Client(
            String clientType,
            String name,
            String INN,
            String BIC,
            String address
    ) {
        this.clientType = clientType;
        this.name = name;
        this.INN = INN;
        this.BIC = BIC;
        this.address = address;
    }

    protected boolean isEqualByCommonFields(Client client) {
        return clientType.equals(client.clientType)
                && name.equals(client.name)
                && INN.equals(client.INN)
                && BIC.equals(client.BIC)
                && address.equals(client.address);
    }
}
