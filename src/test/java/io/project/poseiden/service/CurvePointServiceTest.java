package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.CurvePoint;
import io.project.poseiden.repository.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository repository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setup() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1L)
                .setCurveId(1L)
                .setAsOfDate(new Timestamp(System.currentTimeMillis()))
                .setTerm(10.0)
                .setValue(10.0)
                .setCreationDate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void shouldReturnCurvePoint() {
        when(repository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));

        Optional<CurvePoint> result = curvePointService.findById(curvePoint.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(curvePoint.getId(), result.get().getId());
        verify(repository).findById(curvePoint.getId());
    }

    @Test
    public void shouldReturnEmptyWhenCurvePointIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<CurvePoint> result = curvePointService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnCurvePointWhenExists() {
        when(repository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.getById(curvePoint.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(curvePoint.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenCurvePointNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> curvePointService.getById(2L));
    }

    @Test
    public void shouldReturnAllCurvePoints() {
        when(repository.findAll()).thenReturn(List.of(curvePoint));

        List<CurvePoint> result = curvePointService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllCurvePointsNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<CurvePoint> result = curvePointService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();

        curvePointService.save(curvePoint);

        verify(repository).save(curvePoint);
    }

    @Test
    public void shouldUpdateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1L).setCurveId(1L);

        CurvePoint curvePointUpdated = new CurvePoint();
        curvePointUpdated.setId(curvePoint.getId()).setCurveId(2L);

        when(repository.findById(curvePointUpdated.getId())).thenReturn(Optional.of(curvePoint));

        curvePointService.update(curvePointUpdated);

        verify(repository).save(curvePointUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateCurvePointNotFound() {
        CurvePoint curvePointUpdated = new CurvePoint();
        curvePointUpdated.setId(2L).setCurveId(2L);

        when(repository.findById(curvePointUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> curvePointService.update(curvePointUpdated));
        verify(repository, never()).save(curvePointUpdated);
    }

    @Test
    public void shouldDeleteCurvePoint() {
        when(repository.existsById(1L)).thenReturn(true);

        curvePointService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
