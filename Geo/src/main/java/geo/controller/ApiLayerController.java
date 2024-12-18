package geo.controller;

import geo.dto.GeolocationDataDTO;
import geo.dto.IpRequest;
import geo.dto.UrlRequest;
import org.springframework.http.ResponseEntity;
import geo.service.GeolocationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiLayerController {

    private final GeolocationService geolocationService;


    public ApiLayerController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }


    @PostMapping("/ip")
    public ResponseEntity<GeolocationDataDTO> getGeolocationByIp(@RequestBody IpRequest ipRequest) {
       return geolocationService.getByIp(ipRequest.ip());
    }

    @PostMapping("/url")
    public ResponseEntity<GeolocationDataDTO> getGeolocationByUrl(@RequestBody UrlRequest urlRequest) {
        return geolocationService.getByUrl(urlRequest.url());
    }

    @PostMapping("/ip/add")
    public ResponseEntity<GeolocationDataDTO> findAndSaveGeolocationByIp(@RequestBody IpRequest ipRequest) {
       return geolocationService.findAndSaveByIp(ipRequest.ip());
    }

    @PostMapping("/url/add")
    public ResponseEntity<GeolocationDataDTO> findAndSaveGeolocationByUrl(@RequestBody UrlRequest urlRequest) {
       return geolocationService.findAndSaveByUrl(urlRequest.url());
    }

    @PostMapping("/ip/delete")
    public ResponseEntity<Void> deleteGeolocationByIp(@RequestBody IpRequest ipRequest) {
        return geolocationService.deleteByIp(ipRequest.ip());
    }

    @PostMapping("/url/delete")
    public ResponseEntity<Void> deleteGeolocationByUrl(@RequestBody UrlRequest urlRequest) {
        return geolocationService.deleteByUrl(urlRequest.url());
    }




}
