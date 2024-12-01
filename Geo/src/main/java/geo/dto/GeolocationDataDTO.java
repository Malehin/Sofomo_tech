package geo.dto;

import geo.entity.GeolocationDataEntity;

public record GeolocationDataDTO(
        Double latitude,
        Double longitude,
        String city,
        String regionName,
        String countryName,
        String zip
) {
    public GeolocationDataDTO(GeolocationDataEntity entity) {
        this(
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCity(),
                entity.getRegion_name(),
                entity.getCountry_name(),
                entity.getZip()
        );
    }
}
