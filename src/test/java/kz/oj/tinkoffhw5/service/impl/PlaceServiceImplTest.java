package kz.oj.tinkoffhw5.service.impl;

import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.mapper.PlaceMapper;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.PlaceUpdateRequest;
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
class PlaceServiceImplTest {

    @InjectMocks
    private PlaceServiceImpl locationService;

    @Mock
    private PlaceMapper placeMapper;

    @Mock
    private PlaceRepository placeRepository;

    @Test
    void findAll() {

        // Given
        Place place = mock(Place.class);
        when(placeRepository.findAll()).thenReturn(List.of(place));

        PlaceDto dto = mock(PlaceDto.class);
        when(placeMapper.toDto(place)).thenReturn(dto);

        // When
        List<PlaceDto> result = locationService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void givenNonexistentId_whenFindById_thenThrow() {

        // Given
        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(IllegalArgumentException.class, () -> locationService.findById(id));
    }

    @Test
    void findById() {

        // Given
        Place place = mock(Place.class);

        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        PlaceDto dto = mock(PlaceDto.class);
        when(placeMapper.toDto(place)).thenReturn(dto);

        // When
        PlaceDto result = locationService.findById(id);

        // Then
        assertEquals(dto, result);
    }

    @Test
    void create() {

        // Given
        PlaceCreateRequest request = mock(PlaceCreateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Place place = mock(Place.class);
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        PlaceDto dto = mock(PlaceDto.class);
        when(placeMapper.toDto(place)).thenReturn(dto);

        // When
        PlaceDto result = locationService.create(request);

        // Then
        assertEquals(dto, result);

        ArgumentCaptor<Place> captor = ArgumentCaptor.forClass(Place.class);
        verify(placeRepository).save(captor.capture());

        Place savedPlace = captor.getValue();
        assertEquals(request.getSlug(), savedPlace.getSlug());
        assertEquals(request.getName(), savedPlace.getName());
    }

    @Test
    void givenNonexistentId_whenUpdate_thenThrow() {

        // Given
        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        // When, then
        assertThrows(
                IllegalArgumentException.class,
                () -> locationService.update(id, mock(PlaceUpdateRequest.class))
        );
    }

    @Test
    void givenOnlySlugSet_whenUpdate_onlySlugIsUpdated() {

        // Given
        Place place = mock(Place.class);

        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        PlaceUpdateRequest request = mock(PlaceUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn(null);

        when(placeRepository.save(any(Place.class)))
                .thenReturn(mock(Place.class));

        // When
        locationService.update(id, request);

        // Then
        verify(place).setSlug(request.getSlug());
        verify(placeRepository).save(place);

        verifyNoMoreInteractions(place);
    }

    @Test
    void givenOnlyNameSet_whenUpdate_onlyNameIsUpdated() {

        // Given
        Place place = mock(Place.class);

        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        PlaceUpdateRequest request = mock(PlaceUpdateRequest.class);
        when(request.getSlug()).thenReturn(null);
        when(request.getName()).thenReturn("Алматы");

        when(placeRepository.save(any(Place.class)))
                .thenReturn(mock(Place.class));

        // When
        locationService.update(id, request);

        // Then
        verify(place).setName(request.getName());
        verify(placeRepository).save(place);

        verifyNoMoreInteractions(place);
    }

    @Test
    void update() {

        // Given
        Place place = mock(Place.class);

        UUID id = UUID.randomUUID();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        PlaceUpdateRequest request = mock(PlaceUpdateRequest.class);
        when(request.getSlug()).thenReturn("ala");
        when(request.getName()).thenReturn("Алматы");

        Place savedPlace = mock(Place.class);
        when(placeRepository.save(any(Place.class))).thenReturn(savedPlace);

        PlaceDto dto = mock(PlaceDto.class);
        when(placeMapper.toDto(savedPlace)).thenReturn(dto);

        // When
        PlaceDto result = locationService.update(id, request);

        // Then
        assertEquals(dto, result);

        verify(place).setSlug(request.getSlug());
        verify(place).setName(request.getName());
        verifyNoMoreInteractions(place);

        verify(placeRepository).save(place);
    }


    @Test
    void delete() {

        // Given
        UUID id = UUID.randomUUID();

        // When
        locationService.delete(id);

        // Then
        verify(placeRepository).deleteById(id);
    }
}