package geo.repository;

import geo.entity.UrlData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlDataRepository extends JpaRepository<UrlData, Long> {
    boolean existsByUrl(String url);
}
