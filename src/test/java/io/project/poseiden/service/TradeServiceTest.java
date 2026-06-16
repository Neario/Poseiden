package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.Trade;
import io.project.poseiden.repository.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository repository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private Trade trade;

    @BeforeEach
    public void setup() {
        trade = new Trade();
        trade.setId(1L)
                .setAccount("account")
                .setType("type")
                .setBuyQuantity(10.0)
                .setSellQuantity(2.0)
                .setBuyPrice(5.0)
                .setSellPrice(5.0)
                .setBenchmark("benchmark")
                .setTradeDate(new Timestamp(System.currentTimeMillis()))
                .setSecurity("security")
                .setStatus("status")
                .setTrader("trader")
                .setBook("book")
                .setCreationName("name")
                .setCreationDate(new Timestamp(System.currentTimeMillis()))
                .setRevisionName("revision")
                .setRevisionDate(new Timestamp(System.currentTimeMillis()))
                .setDealName("deal")
                .setDealType("dealType")
                .setSourceListId("sourceListId")
                .setSide("side");
    }

    @Test
    public void shouldReturnTrade() {
        when(repository.findById(trade.getId())).thenReturn(Optional.of(trade));

        Optional<Trade> result = tradeService.findById(trade.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(trade.getId(), result.get().getId());
        verify(repository).findById(trade.getId());
    }

    @Test
    public void shouldReturnEmptyWhenTradeIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Trade> result = tradeService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnTradeWhenExists() {
        when(repository.findById(trade.getId())).thenReturn(Optional.of(trade));

        Trade result = tradeService.getById(trade.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(trade.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenTradeNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> tradeService.getById(2L));
    }

    @Test
    public void shouldReturnAllTrades() {
        when(repository.findAll()).thenReturn(List.of(trade));

        List<Trade> result = tradeService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllTradesNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<Trade> result = tradeService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterTrade() {
        Trade trade = new Trade();

        tradeService.save(trade);

        verify(repository).save(trade);
    }

    @Test
    public void shouldUpdateTrade() {
        Trade trade = new Trade();
        trade.setId(1L).setAccount("account");

        Trade tradeUpdated = new Trade();
        tradeUpdated.setId(trade.getId()).setAccount("accountUpdated");

        when(repository.findById(tradeUpdated.getId())).thenReturn(Optional.of(trade));

        tradeService.update(tradeUpdated);

        verify(repository).save(tradeUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateTradeNotFound() {
        Trade tradeUpdated = new Trade();
        tradeUpdated.setId(2L).setAccount("accountUpdated");

        when(repository.findById(tradeUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> tradeService.update(tradeUpdated));
        verify(repository, never()).save(tradeUpdated);
    }

    @Test
    public void shouldDeleteTrade() {
        tradeService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
