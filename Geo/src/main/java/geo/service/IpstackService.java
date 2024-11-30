package geo.service;


import geo.entity.GeolocationData;
import geo.entity.IpAddress;
import geo.entity.UrlData;
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
    public void getGeolocationDataByIp(String ip) {

        if(ipAddressRepository.existsByIpAddress(ip)){
            log.info("Data for IpAddress: {} Already exist in Database", ip);
        } else{
            String url = IPSTACK_URL + ip + "?access_key=" + IPSTACK_API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response.isBlank()) {
                throw new RuntimeException("API response is empty for IP: " + ip);
            }

            GeolocationData geolocationData = geolocationDataMapper.mapFromResponse(response);

            GeolocationData savedGeolocationData= geolocationDataRepository.save(geolocationData);
            IpAddress ipAddress = new IpAddress();
            ipAddress.setGeolocationDataId(savedGeolocationData.getId());
            ipAddress.setIpAddress(ip);
            ipAddressRepository.save(ipAddress);
        }
    }

    @Transactional
    public void getGeolocationDataByUrl(String urlAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(urlAddress);
            String ip = inetAddress.getHostAddress();

            if(ipAddressRepository.existsByIpAddress(ip)){
                log.info("Data for IpAddress: {} Already exist in Database", ip);
            } else {
                String url = IPSTACK_URL + ip + "?access_key=" + IPSTACK_API_KEY;
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url, String.class);

                if (response.isBlank()) {
                    throw new RuntimeException("API response is empty for IP: " + ip);
                }

                GeolocationData geolocationData = geolocationDataMapper.mapFromResponse(response);

                GeolocationData savedGeolocationData = geolocationDataRepository.save(geolocationData);
                IpAddress ipAddress = new IpAddress();
                ipAddress.setGeolocationDataId(savedGeolocationData.getId());
                ipAddress.setIpAddress(ip);
                ipAddressRepository.save(ipAddress);

                UrlData urlData = new UrlData();
                urlData.setUrl(urlAddress);
                urlData.setGeolocationDataId(savedGeolocationData.getId());
                urlDataRepository.save(urlData);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to resolve URL to IP address: " + urlAddress, e);
        }

    }

}
