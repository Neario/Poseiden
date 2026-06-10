package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.BidList;
import io.project.poseiden.repository.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository repository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    private BidList bidList;

    @BeforeEach
    public void setup() {
        bidList = new BidList();
        bidList.setId(1L)
                .setAccount("testAccount")
                .setType("testType")
                .setBidQuantity(5.0)
                .setAskQuantity(5.0)
                .setBid(10.0)
                .setAsk(10.0)
                .setBenchmark("benchmark")
                .setCommentary("commentary")
                .setSecurity("security")
                .setStatus("status")
                .setTrader("trader")
                .setBook("book")
                .setDealName("dealName")
                .setDealType("dealType")
                .setSourceListId("sourceListId")
                .setSide("side");
    }

    @Test
    public void shouldReturnBidList() {
        when(repository.findById(bidList.getId())).thenReturn(Optional.of(bidList));

        Optional<BidList> result = bidListService.findById(bidList.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bidList.getId(), result.get().getId());
        verify(repository).findById(bidList.getId());
    }

    @Test
    public void shouldReturnEmptyWhenBidListIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<BidList> result = bidListService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnBidListWhenExists() {
        when(repository.findById(bidList.getId())).thenReturn(Optional.of(bidList));

        BidList result = bidListService.getById(bidList.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(bidList.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenBidListNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> bidListService.getById(2L));
    }

    @Test
    public void shouldReturnAllBidLists() {
        when(repository.findAll()).thenReturn(List.of(bidList));

        List<BidList> result = bidListService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllBidListsNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<BidList> result = bidListService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterBidList() {
        BidList bidList = new BidList();

        bidListService.save(bidList);

        verify(repository).save(bidList);
    }

    @Test
    public void shouldUpdateBidList() {
        BidList bidList = new BidList();
        bidList.setId(1L).setAccount("testAccount");

        BidList bidListUpdated = new BidList();
        bidListUpdated.setId(bidList.getId()).setAccount("UpdateAccount");

        when(repository.findById(bidListUpdated.getId())).thenReturn(Optional.of(bidList));

        bidListService.update(bidListUpdated);

        verify(repository).save(bidListUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateBidListNotFound() {
        BidList bidListUpdated = new BidList();
        bidListUpdated.setId(2L).setAccount("UpdateAccount");

        when(repository.findById(bidListUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> bidListService.update(bidListUpdated));
        verify(repository, never()).save(bidListUpdated);
    }

    @Test
    public void shouldDeleteBidList() {
        bidListService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
