package geo.service;


import geo.dto.GeolocationDataDTO;
import geo.entity.GeolocationDataEntity;
import geo.entity.IpAddressEntity;
import geo.entity.UrlDataEntity;
import geo.exception.GeolocationDataNotFoundException;
import geo.repository.GeolocationDataRepository;
import geo.repository.IpAddressRepository;
import geo.repository.UrlDataRepository;
import lombok.AllArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GeolocationService {

    private final IpstackService ipstackService;
    private final GeolocationDataRepository geolocationDataRepository;
    private final IpAddressRepository ipAddressRepository;
    private final UrlDataRepository urlDataRepository;

    public ResponseEntity <GeolocationDataDTO> getByIp(String ipAddress) {
        IpAddressEntity ipAddressEntity = ipAddressRepository.findByIpAddress(ipAddress);
        if (ipAddressEntity == null || ipAddressEntity.getGeolocationDataId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        GeolocationDataEntity geolocationDataEntity =
                geolocationDataRepository.findById(ipAddressEntity.getGeolocationDataId())
                        .orElseThrow(() -> new GeolocationDataNotFoundException("Geolocation data not found"));

        return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
    }

    public ResponseEntity <GeolocationDataDTO> getByUrl(String urlAddress) {
        UrlDataEntity urlDataEntity = urlDataRepository.findByUrl(urlAddress);
        if (urlDataEntity == null || urlDataEntity.getGeolocationDataId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        GeolocationDataEntity geolocationDataEntity =
                geolocationDataRepository.findById(urlDataEntity.getGeolocationDataId())
                        .orElseThrow(() -> new GeolocationDataNotFoundException("Geolocation data not found"));

        return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
    }

        public ResponseEntity<GeolocationDataDTO> findAndSaveByIp (String ipAddress){
            try {
                GeolocationDataEntity geolocationDataEntity = ipstackService.getAndSaveGeolocationDataByIp(ipAddress);
                if (geolocationDataEntity == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
            } catch (Exception e) {
                log.error("Error occurred while fetching geolocation data for IP: {}", ipAddress, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        public ResponseEntity<GeolocationDataDTO> findAndSaveByUrl (String urlAddress){
            try {
                GeolocationDataEntity geolocationDataEntity = ipstackService.getAndSaveGeolocationDataByUrl(urlAddress);
                if (geolocationDataEntity == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
            } catch (Exception e) {
                log.error("Error occurred while fetching geolocation data for URL: {}", urlAddress, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }


    }
