import types.Client;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        for (var filename : args) {
            ClassLoader classLoader = Main.class.getClassLoader();
            String path = Objects.requireNonNull(classLoader.getResource(filename)).getPath();
            Client client = Client.createClientFromJsonFile(path);
            System.out.println(client);
        }
    }
}
