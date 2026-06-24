package io.project.poseiden.controller;

import io.project.poseiden.model.BidList;
import io.project.poseiden.repository.BidListRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class BidListControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;


    private BidList bidList;

    @BeforeEach
    public void setup() {
        bidList = new BidList();
        bidList.setAccount("testAccount")
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

        bidListRepository.save(bidList);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayBidList() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectBidListWhenValidBidList() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                    .with(csrf())
                    .param("account", "testAccount")
                    .param("type", "testType")
                    .param("bidQuantity", "5.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidBidList() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                    .with(csrf())
                    .param("account", "testAccount")
                    .param("type", "")
                    .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithBidList() throws Exception {
        mockMvc.perform(get("/bidList/update/" + 1).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectBidListWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/bidList/update/" + bidList.getId())
                    .with(csrf())
                    .param("account", "updateAccount")
                    .param("type", "updateType")
                    .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidBidList() throws Exception {
        mockMvc.perform(post("/bidList/update/" + 1)
                .with(csrf())
                .param("account", "updateAccount")
                .param("type", "")
                .param("bidQuantity", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenBidListNotFound() throws Exception {
        mockMvc.perform(get("/bidList/update/99"))
            .andExpect(status().isOk())
            .andExpect(view().name("bidList/list"))
            .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectBidListWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/bidList/delete/" + 1).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }
}
