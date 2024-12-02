package geo.service;


import geo.dto.GeolocationDataDTO;
import geo.entity.IpAddressEntity;
import geo.entity.UrlDataEntity;
import geo.exception.GeolocationDataNotFoundException;
import geo.exception.IpAddressNotFoundException;
import geo.exception.UrlAddressNotFoundException;
import geo.repository.GeolocationDataRepository;
import geo.repository.IpAddressRepository;
import geo.repository.UrlDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static geo.utils.GeolocationDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class GeolocationServiceTest {
    @Mock
    private IpstackService ipstackService;

    @Mock
    private GeolocationDataRepository geolocationDataRepository;

    @Mock
    private IpAddressRepository ipAddressRepository;

    @Mock
    private UrlDataRepository urlDataRepository;

    @InjectMocks
    private GeolocationService geolocationService;

    @Test
    void shouldReturnGeolocationDataByIp() {
        // given
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(createIpAddressEntity());
        when(geolocationDataRepository.findById(GEOLOCATION_ID)).thenReturn(Optional.of(createGeolocationDataEntity()));

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.getByIp(TEST_IP);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().city()).isEqualTo(CITY);
        assertThat(response.getBody().zip()).isEqualTo(ZIP);
        assertThat(response.getBody().regionName()).isEqualTo(REGION_NAME);
        assertThat(response.getBody().countryName()).isEqualTo(COUNTRY_NAME);
        assertThat(response.getBody().latitude()).isEqualTo(LATITUDE);
        assertThat(response.getBody().longitude()).isEqualTo(LONGITUDE);
    }

    @Test
    void shouldThrowExceptionWhenIpAddressDataNotFound() {
        // given
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(null);

        // when & then
        assertThrows(IpAddressNotFoundException.class, () -> geolocationService.getByIp(TEST_IP));
    }

    @Test
    void shouldThrowExceptionWhenIpAddressDataNotContainsGeolocationId() {
        // given
        IpAddressEntity ipAddressEntity = createIpAddressEntity();
        ipAddressEntity.setGeolocationDataId(null);
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(ipAddressEntity);

        // when & then
        assertThrows(IpAddressNotFoundException.class, () -> geolocationService.getByIp(TEST_IP));
    }

    @Test
    void shouldReturnGeolocationDataByUrl() {
        // given
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(createUrlDataEntity());
        when(geolocationDataRepository.findById(GEOLOCATION_ID)).thenReturn(Optional.of(createGeolocationDataEntity()));

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.getByUrl(TEST_URL);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().city()).isEqualTo(CITY);
        assertThat(response.getBody().zip()).isEqualTo(ZIP);
        assertThat(response.getBody().regionName()).isEqualTo(REGION_NAME);
        assertThat(response.getBody().countryName()).isEqualTo(COUNTRY_NAME);
        assertThat(response.getBody().latitude()).isEqualTo(LATITUDE);
        assertThat(response.getBody().longitude()).isEqualTo(LONGITUDE);
    }

    @Test
    void shouldThrowExceptionWhenUrlAddressDataNotFound() {
        // given
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(null);

        // when & then
        assertThrows(UrlAddressNotFoundException.class, () -> geolocationService.getByUrl(TEST_URL));
    }

    @Test
    void shouldThrowExceptionWhenUrlAddressDataNotContainsGeolocationId() {
        // given
        UrlDataEntity urlDataEntity = createUrlDataEntity();
        urlDataEntity.setGeolocationDataId(null);
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(urlDataEntity);

        // when & then
        assertThrows(UrlAddressNotFoundException.class, () -> geolocationService.getByUrl(TEST_URL));
    }

    @Test
    void shouldSuccessfullyFindAndSaveByIp() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByIp(TEST_IP)).thenReturn(createGeolocationDataEntity());

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.findAndSaveByIp(TEST_IP);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().city()).isEqualTo(CITY);
        assertThat(response.getBody().zip()).isEqualTo(ZIP);
        assertThat(response.getBody().regionName()).isEqualTo(REGION_NAME);
        assertThat(response.getBody().countryName()).isEqualTo(COUNTRY_NAME);
        assertThat(response.getBody().latitude()).isEqualTo(LATITUDE);
        assertThat(response.getBody().longitude()).isEqualTo(LONGITUDE);
    }

    @Test
    void shouldThrowInternalServerErrorOnIpError() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByIp(TEST_IP)).thenThrow(new RuntimeException("Error"));

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.findAndSaveByIp(TEST_IP);

        // then
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldThrowGeolocationDataNotFoundExceptionOnIpError() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByIp(TEST_IP)).thenReturn(null);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.findAndSaveByIp(TEST_IP));
    }

    @Test
    void shouldSuccessfullyFindAndSaveByUrl() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByUrl(TEST_URL)).thenReturn(createGeolocationDataEntity());

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.findAndSaveByUrl(TEST_URL);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().city()).isEqualTo(CITY);
        assertThat(response.getBody().zip()).isEqualTo(ZIP);
        assertThat(response.getBody().regionName()).isEqualTo(REGION_NAME);
        assertThat(response.getBody().countryName()).isEqualTo(COUNTRY_NAME);
        assertThat(response.getBody().latitude()).isEqualTo(LATITUDE);
        assertThat(response.getBody().longitude()).isEqualTo(LONGITUDE);
    }

    @Test
    void shouldThrowInternalServerErrorOnUrlError() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByUrl(TEST_IP)).thenThrow(new RuntimeException("Error"));

        // when
        ResponseEntity<GeolocationDataDTO> response = geolocationService.findAndSaveByUrl(TEST_URL);

        // then
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldThrowGeolocationDataNotFoundExceptionOnUrlError() {
        // given
        when(ipstackService.getAndSaveGeolocationDataByUrl(TEST_URL)).thenReturn(null);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.findAndSaveByUrl(TEST_URL));
    }


    @Test
    void shouldSuccessfullyDeleteByIp() {
        // given
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(createIpAddressEntity());

        // when
        ResponseEntity<Void> response = geolocationService.deleteByIp(TEST_IP);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldThrowExceptionWhenIpDataNotFoundOnDelete() {
        // given
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(null);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.deleteByIp(TEST_IP));
    }
    @Test
    void shouldThrowExceptionWhenIpDataWithoutGeolocationIdFoundOnDelete() {
        // given
        IpAddressEntity ipAddressEntity = createIpAddressEntity();
        ipAddressEntity.setGeolocationDataId(null);
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(ipAddressEntity);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.deleteByIp(TEST_IP));
    }

    @Test
    void shouldReturnInternalServerErrorWhenExceptionOccursDuringDeletionByIp() {
        // Given
        when(ipAddressRepository.findByIpAddress(TEST_IP)).thenReturn(createIpAddressEntity());

        // When
        doThrow(new RuntimeException("Deletion failed")).when(geolocationDataRepository).deleteById(GEOLOCATION_ID);
        ResponseEntity<Void> response = geolocationService.deleteByIp(TEST_IP);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldSuccessfullyDeleteByUrl() {
        // given
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(createUrlDataEntity());

        // when
        ResponseEntity<Void> response = geolocationService.deleteByUrl(TEST_URL);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldThrowExceptionWhenUrlDataNotFoundOnDelete() {
        // given
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(null);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.deleteByUrl(TEST_URL));
    }

    @Test
    void shouldThrowExceptionWhenUrlDataWithoutGeolocationIdFoundOnDelete() {
        // given
        UrlDataEntity urlDataEntity = createUrlDataEntity();
        urlDataEntity.setGeolocationDataId(null);
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(urlDataEntity);

        // when & then
        assertThrows(GeolocationDataNotFoundException.class, () -> geolocationService.deleteByUrl(TEST_URL));
    }

    @Test
    void shouldReturnInternalServerErrorWhenExceptionOccursDuringDeletionByUrl() {
        // Given
        when(urlDataRepository.findByUrl(TEST_URL)).thenReturn(createUrlDataEntity());

        // When
        doThrow(new RuntimeException("Deletion failed")).when(geolocationDataRepository).deleteById(GEOLOCATION_ID);
        ResponseEntity<Void> response = geolocationService.deleteByUrl(TEST_URL);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }
}