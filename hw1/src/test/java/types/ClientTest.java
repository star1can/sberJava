package types;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import types.enums.EnterpriseType;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    @Order(1)
    @Tag("ClientTest")
    void testIndividual() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("individual.json")).getPath();

        Client individual = Client.createClientFromJsonFile(path);
        Individual individualExpected = new Individual("INDIVIDUAL", "ИП Годунов Борис Владимирович", "773415460205", "134558225", "223257, г. Москва, Нижний Кисельный пер., д.4", "321774600501001", "27 августа 2021 г.");
        assertEquals(individual, individualExpected);
        System.out.println("======TEST ONE SUCCESSFUL=======");
    }

    @Test
    @Order(2)
    @Tag("ClientTest")
    void testHolding() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("holding.json")).getPath();

        Client holding = Client.createClientFromJsonFile(path);
        Holding holdingExpected = new Holding("HOLDING", "ПАО Сбербанк", "7707083893", "044525225", "Россия, Москва, 117312, ул. Вавилова, д. 19", new String[]{"АО Сбербанк", "ООО СберИнфра", "АО Сбертех", "ООО\"Сити-Мобил\""});
        assertEquals(holding, holdingExpected);
        System.out.println("======TEST TWO SUCCESSFUL=======");
    }

    @Test
    @Order(3)
    @Tag("ClientTest")
    void testLegalEntity() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource("legal-entity.json")).getPath();

        Client legalEntity = Client.createClientFromJsonFile(path);
        LegalEntity legalEntityExpected = new LegalEntity("LEGAL_ENTITY", "ООО \"ГФ СБОРНЫЕ ГРУЗЫ\"", "9710018009", "044525225", "123557, г. Москва, Электрический пер., д. 3/10 стр. 1, этаж/пом. 5/I ком./офис 3/а4д", EnterpriseType.OOO, "Перминов Михаил Валерьевич");
        assertEquals(legalEntity, legalEntityExpected);
        System.out.println("======TEST THREE SUCCESSFUL=======");
    }
}

