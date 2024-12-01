package geo.service;


import geo.dto.GeolocationDataDTO;
import geo.entity.GeolocationDataEntity;
import geo.entity.IpAddressEntity;
import geo.entity.UrlDataEntity;
import geo.exception.GeolocationDataNotFoundException;
import geo.exception.IpAddressNotFoundException;
import geo.exception.UrlAddressNotFoundException;
import geo.repository.GeolocationDataRepository;
import geo.repository.IpAddressRepository;
import geo.repository.UrlDataRepository;
import jakarta.transaction.Transactional;
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

    public ResponseEntity<GeolocationDataDTO> getByIp(String ipAddress) {
        IpAddressEntity ipAddressEntity = ipAddressRepository.findByIpAddress(ipAddress);
        if (ipAddressEntity == null || ipAddressEntity.getGeolocationDataId() == null) {
            throw new IpAddressNotFoundException("IpAddress data not found or is incomplete for Ip: " + ipAddress);
        }
        GeolocationDataEntity geolocationDataEntity =
                geolocationDataRepository.findById(ipAddressEntity.getGeolocationDataId())
                        .orElseThrow(() -> new GeolocationDataNotFoundException("Geolocation data not found"));

        return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
    }

    public ResponseEntity<GeolocationDataDTO> getByUrl(String urlAddress) {
        UrlDataEntity urlDataEntity = urlDataRepository.findByUrl(urlAddress);
        if (urlDataEntity == null || urlDataEntity.getGeolocationDataId() == null) {
            throw new UrlAddressNotFoundException("UrlAddress data not found or is incomplete for Url: " + urlAddress);

        }
        GeolocationDataEntity geolocationDataEntity =
                geolocationDataRepository.findById(urlDataEntity.getGeolocationDataId())
                        .orElseThrow(() -> new GeolocationDataNotFoundException("Geolocation data not found"));

        return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
    }

    public ResponseEntity<GeolocationDataDTO> findAndSaveByIp(String ipAddress) {
        try {
            GeolocationDataEntity geolocationDataEntity = ipstackService.getAndSaveGeolocationDataByIp(ipAddress);
            if (geolocationDataEntity == null) {
                throw new GeolocationDataNotFoundException("Geolocation data not found for Ip: " + ipAddress);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
        } catch (Exception e) {
            log.error("Error occurred while fetching geolocation data for IP: {}", ipAddress, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<GeolocationDataDTO> findAndSaveByUrl(String urlAddress) {
        try {
            GeolocationDataEntity geolocationDataEntity = ipstackService.getAndSaveGeolocationDataByUrl(urlAddress);
            if (geolocationDataEntity == null) {
                throw new GeolocationDataNotFoundException("Geolocation data not found for Url: " + urlAddress);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new GeolocationDataDTO(geolocationDataEntity));
        } catch (Exception e) {
            log.error("Error occurred while fetching geolocation data for URL: {}", urlAddress, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteByUrl(String urlAddress) {
        UrlDataEntity urlDataEntity = urlDataRepository.findByUrl(urlAddress);

        if (urlDataEntity == null || urlDataEntity.getGeolocationDataId() == null) {
            throw new GeolocationDataNotFoundException("Geolocation data not found for URL: " + urlAddress);
        }

        try {
            geolocationDataRepository.deleteById(urlDataEntity.getGeolocationDataId());
            urlDataRepository.deleteById(urlDataEntity.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<Void> deleteByIp(String ipAddress) {
        IpAddressEntity ipAddressEntity = ipAddressRepository.findByIpAddress(ipAddress);

        if (ipAddressEntity == null || ipAddressEntity.getGeolocationDataId() == null)  {
            throw new GeolocationDataNotFoundException("Geolocation data not found for Ip: " + ipAddress);
        }

        try {
            geolocationDataRepository.deleteById(ipAddressEntity.getGeolocationDataId());
            ipAddressRepository.deleteById(ipAddressEntity.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
