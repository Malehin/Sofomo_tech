package geo.utils;

import geo.entity.GeolocationDataEntity;
import geo.entity.IpAddressEntity;
import geo.entity.UrlDataEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeolocationDataFactory {

    public static final String TEST_IP = "192.168.0.1";
    public static final String TEST_URL = "example.com";
    public static final Long GEOLOCATION_ID = 5L;
    public static final String TEST_API_KEY = "57dc58e5b56d3d570a2865346f72452b";

    public static final String CITY = "San Francisco";
    public static final String ZIP = "94103";
    public static final Double LATITUDE = 37.7749;
    public static final Double LONGITUDE = -122.4194;
    public static final String COUNTRY_NAME = "USA";
    public static final String REGION_NAME = "California";

    public static String createGeolocationDataInJson(){
        return  """
                {
                    "latitude": 37.7749,
                    "longitude": -122.4194,
                    "city": "San Francisco",
                    "region_name": "California",
                    "country_name": "USA",
                    "zip": "94103"
                }
                """;
    }

    public static GeolocationDataEntity createGeolocationDataEntity(){
        GeolocationDataEntity geolocationDataEntity = new GeolocationDataEntity();
        geolocationDataEntity.setCity(CITY);
        geolocationDataEntity.setZip(ZIP);
        geolocationDataEntity.setLatitude(LATITUDE);
        geolocationDataEntity.setLongitude(LONGITUDE);
        geolocationDataEntity.setCountry_name(COUNTRY_NAME);
        geolocationDataEntity.setRegion_name(REGION_NAME);

        return geolocationDataEntity;
    }

    public static IpAddressEntity createIpAddressEntity(){
        IpAddressEntity ipAddressEntity = new IpAddressEntity();
        ipAddressEntity.setIpAddress(TEST_IP);
        ipAddressEntity.setGeolocationDataId(GEOLOCATION_ID);

        return ipAddressEntity;
    }

    public static UrlDataEntity createUrlDataEntity(){
        UrlDataEntity urlDataEntity = new UrlDataEntity();
        urlDataEntity.setUrl(TEST_URL);
        urlDataEntity.setGeolocationDataId(GEOLOCATION_ID);

        return urlDataEntity;
    }

}
