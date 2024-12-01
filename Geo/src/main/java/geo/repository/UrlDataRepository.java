package geo.repository;

import geo.entity.UrlDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlDataRepository extends JpaRepository<UrlDataEntity, Long> {
    boolean existsByUrl(String urlAddress);

    UrlDataEntity findByUrl(String urlAddress);
}
