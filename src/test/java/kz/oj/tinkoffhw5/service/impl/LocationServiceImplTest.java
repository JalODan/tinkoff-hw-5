package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.mapper.LocationMapper;
import kz.oj.tinkoffhw5.repository.LocationRepository;
import kz.oj.tinkoffhw5.web.rest.v1.dto.LocationDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.LocationUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationMapper locationMapper;

    @Mock
    private LocationRepository locationRepository;

    @Test
    void findAll() {

        // Given
        Location location = mock(Location.class);
        when(locationRepository.findAll()).thenReturn(List.of(location));

        LocationDto dto = mock(LocationDto.class);
        when(locationMapper.toDto(location)).thenReturn(dto);

        // When
        List<LocationDto> result = locationService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void givenNonexistentId_whenFindById_thenThrow() {

        // Given
        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(IllegalArgumentException.class, () -> locationService.findById(id));
    }

    @Test
    void findById() {

        // Given
        Location location = mock(Location.class);

        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        LocationDto dto = mock(LocationDto.class);
        when(locationMapper.toDto(location)).thenReturn(dto);

        // When
        LocationDto result = locationService.findById(id);

        // Then
        assertEquals(dto, result);
    }

    @Test
    void create() {

        // Given
        LocationCreateRequest request = mock(LocationCreateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Location location = mock(Location.class);
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDto dto = mock(LocationDto.class);
        when(locationMapper.toDto(location)).thenReturn(dto);

        // When
        LocationDto result = locationService.create(request);

        // Then
        assertEquals(dto, result);

        ArgumentCaptor<Location> captor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepository).save(captor.capture());

        Location savedLocation = captor.getValue();
        assertEquals(request.getSlug(), savedLocation.getSlug());
        assertEquals(request.getName(), savedLocation.getName());
    }

    @Test
    void givenNonexistentId_whenUpdate_thenThrow() {

        // Given
        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(
                IllegalArgumentException.class,
                () -> locationService.update(id, mock(LocationUpdateRequest.class))
        );
    }

    @Test
    void givenOnlySlugSet_whenUpdate_onlySlugIsUpdated() {

        // Given
        Location location = mock(Location.class);

        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        LocationUpdateRequest request = mock(LocationUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn(null);

        when(locationRepository.save(any(Location.class)))
                .thenReturn(mock(Location.class));

        // When
        locationService.update(id, request);

        // Then
        verify(location).setSlug(request.getSlug());
        verify(locationRepository).save(location);

        verifyNoMoreInteractions(location);
    }

    @Test
    void givenOnlyNameSet_whenUpdate_onlyNameIsUpdated() {

        // Given
        Location location = mock(Location.class);

        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        LocationUpdateRequest request = mock(LocationUpdateRequest.class);
        when(request.getSlug()).thenReturn(null);
        when(request.getName()).thenReturn("Алматы");

        when(locationRepository.save(any(Location.class)))
                .thenReturn(mock(Location.class));

        // When
        locationService.update(id, request);

        // Then
        verify(location).setName(request.getName());
        verify(locationRepository).save(location);

        verifyNoMoreInteractions(location);
    }

    @Test
    void update() {

        // Given
        Location location = mock(Location.class);

        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        LocationUpdateRequest request = mock(LocationUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Location savedLocation = mock(Location.class);
        when(locationRepository.save(any(Location.class))).thenReturn(savedLocation);

        LocationDto dto = mock(LocationDto.class);
        when(locationMapper.toDto(savedLocation)).thenReturn(dto);

        // When
        LocationDto result = locationService.update(id, request);

        // Then
        assertEquals(dto, result);

        verify(location).setSlug(request.getSlug());
        verify(location).setName(request.getName());
        verifyNoMoreInteractions(location);

        verify(locationRepository).save(location);
    }


    @Test
    void delete() {

        // Given
        UUID id = UUID.randomUUID();

        // When
        locationService.delete(id);

        // Then
        verify(locationRepository).deleteById(id);
    }
}