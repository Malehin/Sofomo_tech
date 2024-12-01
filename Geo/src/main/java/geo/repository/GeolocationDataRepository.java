package geo.repository;

import geo.entity.GeolocationDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocationDataRepository extends JpaRepository<GeolocationDataEntity, Long> {
}
