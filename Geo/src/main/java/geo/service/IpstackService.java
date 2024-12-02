package geo.service;

import geo.entity.GeolocationDataEntity;
import geo.entity.IpAddressEntity;
import geo.entity.UrlDataEntity;
import geo.exception.EmptyApiResponseException;
import geo.exception.UrlResolutionException;
import geo.mapper.GeolocationDataMapper;
import geo.repository.GeolocationDataRepository;
import geo.repository.IpAddressRepository;
import geo.repository.UrlDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Slf4j
@AllArgsConstructor
public class IpstackService {
    private static final String IPSTACK_URL = "https://api.ipstack.com/";
    private static final String IPSTACK_API_KEY = "57dc58e5b56d3d570a2865346f72452b";

    private final GeolocationDataRepository geolocationDataRepository;
    private final IpAddressRepository ipAddressRepository;
    private final GeolocationDataMapper geolocationDataMapper;
    private final UrlDataRepository urlDataRepository;

    @Transactional
    public GeolocationDataEntity getAndSaveGeolocationDataByIp(String ipAddress) {
        log.info("Starting geolocation data retrieval for IP address: {}", ipAddress);

        if (ipAddressRepository.existsByIpAddress(ipAddress)) {
            log.info("Data for IpAddress: {} Already exist in Database", ipAddress);
            return null;
        } else {
            String url = IPSTACK_URL + ipAddress + "?access_key=" + IPSTACK_API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.isBlank()) {
                log.error("API response is empty for IP: {}", ipAddress);
                throw new EmptyApiResponseException("API response is empty for IP: " + ipAddress);
            }

            GeolocationDataEntity geolocationDataEntity = geolocationDataMapper.mapFromResponse(response);
            GeolocationDataEntity savedGeolocationDataEntity = geolocationDataRepository.save(geolocationDataEntity);
            log.info("Saved geolocation data for IP address {} ", ipAddress);

            IpAddressEntity ipAddressEntity = new IpAddressEntity();
            ipAddressEntity.setGeolocationDataId(savedGeolocationDataEntity.getId());
            ipAddressEntity.setIpAddress(ipAddress);
            ipAddressRepository.save(ipAddressEntity);

            log.info("Saved IP address for: {}", ipAddress);

            return savedGeolocationDataEntity;
        }
    }

    @Transactional
    public GeolocationDataEntity getAndSaveGeolocationDataByUrl(String urlAddress) {
        log.info("Starting geolocation data retrieval for Url address: {}", urlAddress);

        try {
            InetAddress inetAddress = InetAddress.getByName(urlAddress);
            String ipAddress = inetAddress.getHostAddress();

            if (urlDataRepository.existsByUrl(urlAddress)) {
                log.info("Data for UrlAddress: {} Already exist in Database", urlAddress);
                return null;
            } else {
                String url = IPSTACK_URL + ipAddress + "?access_key=" + IPSTACK_API_KEY;
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url, String.class);

                if (response == null || response.isBlank()) {
                    log.error("API response is empty for Url: {}", urlAddress);
                    throw new EmptyApiResponseException("API response is empty for IP: " + ipAddress);
                }

                GeolocationDataEntity geolocationDataEntity = geolocationDataMapper.mapFromResponse(response);
                GeolocationDataEntity savedGeolocationDataEntity = geolocationDataRepository.save(geolocationDataEntity);
                log.info("Saved geolocation data for Url address {} ", urlAddress);

                UrlDataEntity urlDataEntity = new UrlDataEntity();
                urlDataEntity.setUrl(urlAddress);
                urlDataEntity.setGeolocationDataId(savedGeolocationDataEntity.getId());
                urlDataRepository.save(urlDataEntity);

                log.info("Saved Url address for: {}", urlAddress);

                return savedGeolocationDataEntity;
            }
        } catch (UnknownHostException e) {
            throw new UrlResolutionException("Unable to resolve URL to IP address: " + urlAddress);
        }

    }
}
