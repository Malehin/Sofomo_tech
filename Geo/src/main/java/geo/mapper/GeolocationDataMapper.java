package geo.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import geo.entity.GeolocationData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GeolocationDataMapper {
    private final ObjectMapper objectMapper;


    public GeolocationData mapFromResponse(String response) {
        try {

            return objectMapper.readValue(response, GeolocationData.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON response to GeolocationData", e);
        }
    }
}