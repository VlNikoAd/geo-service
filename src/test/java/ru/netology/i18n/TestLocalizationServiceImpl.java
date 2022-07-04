package ru.netology.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.entity.Country.RUSSIA;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

class TestLocalizationServiceImpl {
    LocalizationService localizationService;

    @BeforeEach
    public void init() {
        System.out.println("Test started..");
        localizationService = new LocalizationServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("Test completed!");
    }

    @Test
    public void testLocale() {
        String expended = "Добро пожаловать";

        String actual = localizationService.locale(RUSSIA);

        assertEquals(expended, actual);
    }
}
