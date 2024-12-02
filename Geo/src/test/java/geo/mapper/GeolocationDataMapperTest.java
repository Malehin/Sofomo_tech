package geo.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import geo.entity.GeolocationDataEntity;
import geo.utils.GeolocationDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeolocationDataMapperTest {

    private GeolocationDataMapper mapper;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        mapper = new GeolocationDataMapper(objectMapper);
    }

    @Test
    void shouldSuccessfullyMapResponse() {
        // given
        String jsonResponse = GeolocationDataFactory.createGeolocationDataInJson();

        GeolocationDataEntity expectedEntity = new GeolocationDataEntity();
        expectedEntity.setLatitude(37.7749);
        expectedEntity.setLongitude(-122.4194);
        expectedEntity.setCity("San Francisco");
        expectedEntity.setRegion_name("California");
        expectedEntity.setCountry_name("USA");
        expectedEntity.setZip("94103");

        // when
        GeolocationDataEntity result = mapper.mapFromResponse(jsonResponse);

        // then
        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("createdAt") // Exclude the 'createdAt' field from comparison
                .isEqualTo(expectedEntity);
    }

    @Test
    void shouldThrowExceptionWhenResponseIsInvalid() {
        //given
        String invalidJson = "{invalid json}";

        //when & then
        assertThrows(RuntimeException.class, () -> mapper.mapFromResponse(invalidJson));
    }

    @Test
    void shouldThrowExceptionWhenResponseIsNull() {
        //when & then
        assertThrows(RuntimeException.class, () -> mapper.mapFromResponse(null));
    }
}