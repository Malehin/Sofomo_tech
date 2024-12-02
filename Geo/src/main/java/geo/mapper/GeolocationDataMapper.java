package geo.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import geo.entity.GeolocationDataEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GeolocationDataMapper {
    private final ObjectMapper objectMapper;

    public GeolocationDataEntity mapFromResponse(String response) {
        try {
            return objectMapper.readValue(response, GeolocationDataEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON response to GeolocationDataEntity", e);
        }
    }
}