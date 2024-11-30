package geo.service;


import geo.repository.GeolocationDataRepository;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeolocationService {

    private final IpstackService ipstackService;
    private final GeolocationDataRepository geolocationDataRepository;

    public void getByIp(String ip) {
    }

    public void findAndSaveByIp(String ipAddress) {
        ipstackService.getGeolocationDataByIp(ipAddress);
    }

    public void findAndSaveByUrl(String urlAddress) {
        ipstackService.getGeolocationDataByUrl(urlAddress);
    }


}
