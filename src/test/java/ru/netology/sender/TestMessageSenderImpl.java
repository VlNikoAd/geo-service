package ru.netology.sender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;

class TestMessageSenderImpl {

    @BeforeEach
    public void init() {
        System.out.println("Test started..");
    }

    @AfterEach
    public void finished() {
        System.out.println("\nTest completed!");
    }

    @Test
    public void testSendRussianMessage() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> map = Map.of("x-real-ip", "172.0.32.11");

        String expected = "Добро пожаловать";
        String actual = messageSender.send(map);

        assertEquals(expected, actual);
    }

    @Test
    public void testSendEnglishMessage() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> map = Map.of("x-real-ip", "96.44.183.149");

        String expected = "Welcome";
        String actual = messageSender.send(map);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("sources")
    public void testSendWithParameters(String ip, Location location, Country Country, String message) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country))
                .thenReturn(message);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> map = Map.of("x-real-ip", ip);

        String actual = messageSender.send(map);

        assertEquals(message, actual);
    }

    private static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), Country.USA, "Welcome"),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15), Country.RUSSIA, "Добро пожаловать"));
    }
}
