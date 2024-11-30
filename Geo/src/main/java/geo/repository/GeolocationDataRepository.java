package geo.repository;

import geo.entity.GeolocationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocationDataRepository extends JpaRepository<GeolocationData, Long> {
}
