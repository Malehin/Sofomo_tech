package geo.service;


import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeolocationService {

    private final IpstackService ipstackService;

    public ResponseEntity<String> getByIp(String ip) {
        return ipstackService.getGeolocationDataByIp(ip);
    }
}
