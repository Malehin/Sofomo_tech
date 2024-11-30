package service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class IpstackService {
    private static final String IPSTACK_URL = "https://api.ipstack.com/";
    private static final String IPSTACK_API_KEY = "57dc58e5b56d3d570a2865346f72452b";

    public void getGeolocationDataByIp(String ip ) throws JSONException {
        String url = IPSTACK_URL + ip + "?access_key=" + IPSTACK_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String respone = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(respone);


        System.out.println("Country: " + json.getString("country_name"));
        System.out.println("City: " + json.getString("city"));
        System.out.println("Latitude: " + json.getDouble("latitude"));
        System.out.println("Longitude: " + json.getDouble("longitude"));

    }
}
