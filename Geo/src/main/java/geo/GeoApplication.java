package geo;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.IpstackService;

@SpringBootApplication
public class GeoApplication {

	public static void main(String[] args) throws JSONException {
		SpringApplication.run(GeoApplication.class, args);

		IpstackService  ipstackService= new IpstackService();
		ipstackService.getGeolocationDataByIp("162.158.48.181");


	}
}
