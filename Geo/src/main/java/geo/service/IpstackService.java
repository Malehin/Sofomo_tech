package geo.service;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IpstackService {
    private static final String IPSTACK_URL = "https://api.ipstack.com/";
    private static final String IPSTACK_API_KEY = "57dc58e5b56d3d570a2865346f72452b";

    public ResponseEntity<String> getGeolocationDataByIp(String ip) {
        String url = IPSTACK_URL + ip + "?access_key=" + IPSTACK_API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        try {
            String respone = restTemplate.getForObject(url, String.class);
//            System.out.println("Country: " + json.getString("country_name"));
//            System.out.println("City: " + json.getString("city"));
//            System.out.println("Latitude: " + json.getDouble("latitude"));
//            System.out.println("Longitude: " + json.getDouble("longitude"));
            return ResponseEntity.status(HttpStatus.OK).body(respone);
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid JSON response: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }

    }
}
