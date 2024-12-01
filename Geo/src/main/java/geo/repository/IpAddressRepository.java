package geo.repository;


import geo.entity.IpAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpAddressRepository extends JpaRepository<IpAddressEntity, Long> {

    boolean existsByIpAddress(String ipAddress);

    IpAddressEntity findByIpAddress(String ipAddress);
}
