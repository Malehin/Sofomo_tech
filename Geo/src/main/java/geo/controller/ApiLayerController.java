package geo.controller;

import geo.dto.IpRequest;
import geo.dto.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import geo.service.GeolocationService;

@RestController
public class ApiLayerController {

    private final GeolocationService geolocationService;

    @Autowired
    public ApiLayerController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }


    @GetMapping("/ip")
    public void getGeolocationByIp(@RequestBody IpRequest ipRequest) {
       geolocationService.getByIp(ipRequest.ip());
    }


    @PostMapping("generate/ip")
    public void findAndSaveGeolocationByIp(@RequestBody IpRequest ipRequest) {
        geolocationService.findAndSaveByIp(ipRequest.ip());
    }

    @PostMapping("generate/url")
    public void findAndSaveGeolocationByUrl(@RequestBody UrlRequest urlRequest) {
        geolocationService.findAndSaveByUrl(urlRequest.url());
    }
}
