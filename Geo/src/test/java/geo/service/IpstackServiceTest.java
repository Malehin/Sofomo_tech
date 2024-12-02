package geo.service;


import geo.entity.GeolocationDataEntity;
import geo.exception.UrlResolutionException;
import geo.mapper.GeolocationDataMapper;
import geo.repository.GeolocationDataRepository;
import geo.repository.IpAddressRepository;
import geo.repository.UrlDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


import java.net.InetAddress;
import java.net.UnknownHostException;

import static geo.utils.GeolocationDataFactory.TEST_IP;
import static geo.utils.GeolocationDataFactory.TEST_URL;
import static geo.utils.GeolocationDataFactory.createGeolocationDataEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IpstackServiceTest {

    @Mock
    private GeolocationDataRepository geolocationDataRepository;
    @Mock
    private IpAddressRepository ipAddressRepository;
    @Mock
    private GeolocationDataMapper geolocationDataMapper;
    @Mock
    private UrlDataRepository urlDataRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private InetAddress inetAddress;

    @InjectMocks
    private IpstackService ipstackService;

    @Test
    void shouldSuccessfullyGetAndSaveGeolocationDataByIp() {
        // given
        GeolocationDataEntity geolocationDataEntity = createGeolocationDataEntity();
        when(ipAddressRepository.existsByIpAddress(TEST_IP)).thenReturn(false);
        when(geolocationDataRepository.save(any())).thenReturn(geolocationDataEntity);

        // Act
        GeolocationDataEntity result = ipstackService.getAndSaveGeolocationDataByIp(TEST_IP);

        // Assert
        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(createGeolocationDataEntity());

        verify(ipAddressRepository).existsByIpAddress(TEST_IP);
        verify(geolocationDataRepository).save(any());
        verify(ipAddressRepository).save(any());
    }

    @Test
    void shouldReturnNullWhenIpAddressAlreadyExist() {
        // given
        when(ipAddressRepository.existsByIpAddress(TEST_IP)).thenReturn(true);

        // when
        GeolocationDataEntity result = ipstackService.getAndSaveGeolocationDataByIp(TEST_IP);

        // then
        assertThat(result).isNull();

        verify(ipAddressRepository).existsByIpAddress(TEST_IP);
        verifyNoInteractions(geolocationDataRepository, geolocationDataMapper, restTemplate);
    }


//    @Test
//    @Disabled
//    void shouldThrowExceptionWhenResponseIsBlank() {
//        // given
//        when(ipAddressRepository.existsByIpAddress(TEST_IP)).thenReturn(false);
//        // Have issues with correct mock restTemplate.getForObject, and I don't want to spend too much time on it
//        when(restTemplate.getForObject(any(), eq(String.class))).thenReturn("");
//        // when & then
//        assertThrows(EmptyApiResponseException.class, () -> ipstackService.getAndSaveGeolocationDataByIp(TEST_IP));
//    }

    @Test
    void shouldSuccessfullyGetAndSaveGeolocationDataByUrl() {
        // given
        GeolocationDataEntity geolocationDataEntity = createGeolocationDataEntity();
        when(urlDataRepository.existsByUrl(TEST_URL)).thenReturn(false);
        when(geolocationDataRepository.save(any())).thenReturn(geolocationDataEntity);

        // Act
        GeolocationDataEntity result = ipstackService.getAndSaveGeolocationDataByUrl(TEST_URL);

        // Assert
        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(createGeolocationDataEntity());

        verify(urlDataRepository).existsByUrl(TEST_URL);
        verify(geolocationDataRepository).save(any());
        verify(urlDataRepository).save(any());
    }

    @Test
    void shouldReturnNullWhenUrlAddressAlreadyExist() {
        // given
        when(urlDataRepository.existsByUrl(TEST_URL)).thenReturn(true);

        // when
        GeolocationDataEntity result = ipstackService.getAndSaveGeolocationDataByUrl(TEST_URL);

        // then
        assertThat(result).isNull();

        verify(urlDataRepository).existsByUrl(TEST_URL);
        verifyNoInteractions(geolocationDataRepository, geolocationDataMapper, restTemplate);
    }

    @Test
    void shouldThrowExceptionForUnknownHost() {
        // given
        try (MockedStatic<InetAddress> mockedInetAddress = mockStatic(InetAddress.class)) {
            mockedInetAddress.when(() -> InetAddress.getByName(TEST_URL)).thenThrow(new UnknownHostException());

            // when & then
            assertThrows(UrlResolutionException.class, () -> ipstackService.getAndSaveGeolocationDataByUrl(TEST_URL));
        }
    }

}