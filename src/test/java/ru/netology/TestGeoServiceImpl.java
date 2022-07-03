package ru.netology;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

class TestGeoServiceImpl {
    GeoService geoService;

    @BeforeEach
    public void init() {
        System.out.println("Test started..");
        geoService = new GeoServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("Test completed!");
    }

    @Test
    public void testByIp() {
        String ip = "172.0.32.11";
        Location expected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        Location actual = geoService.byIp(ip);

        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getBuiling(), actual.getBuiling());
    }
}