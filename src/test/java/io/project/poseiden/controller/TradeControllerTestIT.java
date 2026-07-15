package io.project.poseiden.controller;

import io.project.poseiden.model.Trade;
import io.project.poseiden.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class TradeControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    private Trade trade;

    @BeforeEach
    public void setup() {
        trade = new Trade();
        trade.setAccount("account")
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

        tradeRepository.save(trade);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayTrade() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectTradeWhenValidTrade() throws Exception {
        mockMvc.perform(post("/trade/validate")
                    .with(csrf())
                    .param("account", "account")
                    .param("type", "type")
                    .param("buyQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidTrade() throws Exception {
        mockMvc.perform(post("/trade/validate")
                    .with(csrf())
                    .param("account", "account")
                    .param("type", "")
                    .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithTrade() throws Exception {
        mockMvc.perform(get("/trade/update/" + trade.getId())
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectTradeWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/trade/update/" + trade.getId())
                    .with(csrf())
                    .param("account", "account")
                    .param("type", "updateType")
                    .param("buyQuantity", "8.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidTrade() throws Exception {
        mockMvc.perform(post("/trade/update/" + trade.getId())
                .with(csrf())
                .param("account", "updateAccount")
                .param("type", "")
                .param("buyQuantity", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenTradeNotFound() throws Exception {
        mockMvc.perform(get("/trade/update/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectTradeWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/trade/delete/" + trade.getId())
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenDeleteTradeNotFound() throws Exception {
        mockMvc.perform(get("/trade/delete/99")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("errors"));
    }
}
