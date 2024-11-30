package geo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import geo.service.GeolocationService;

@RestController
@RequestMapping("/geolocation")
public class ApiLayerController {

    private final GeolocationService geolocationService;

    @Autowired
    public ApiLayerController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }


    @GetMapping("/ip/{ip}")
    public ResponseEntity<String> getAndSaveGeolocationByIp(@PathVariable String ip) {
        return geolocationService.getByIp(ip);
    }

}
