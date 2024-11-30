package geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "IP_ADDRESS")
public class IpAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IP_ADDRESS", nullable = false, unique = true)
    private String ipAddress;

    @Column(name = "GEOLOCATION_DATA_ID", nullable = false)
    private Long geolocationDataId;

}