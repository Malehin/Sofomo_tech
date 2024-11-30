package geo.repository;


import geo.entity.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    boolean existsByIpAddress(String ipAddress);
}
