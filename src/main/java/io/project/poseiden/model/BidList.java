package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@DynamicUpdate
@Entity
@Table(name = "bidlist")
public class BidList implements CrudModel<BidList> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;


    public BidList update(BidList bidList) {
        setAccount(bidList.getAccount());
        setType(bidList.getType());
        setBidQuantity(bidList.getBidQuantity());
        setAskQuantity(bidList.getAskQuantity());
        setBid(bidList.getBid());
        setAsk(bidList.getAsk());
        setBenchmark(bidList.getBenchmark());
        setBidListDate(bidList.getBidListDate());
        setCommentary(bidList.getCommentary());
        setSecurity(bidList.getSecurity());
        setStatus(bidList.getStatus());
        setTrader(bidList.getTrader());
        setBook(bidList.getBook());
        setCreationName(bidList.getCreationName());
        setCreationDate(bidList.getCreationDate());
        setRevisionName(bidList.getRevisionName());
        setRevisionDate(bidList.getRevisionDate());
        setDealName(bidList.getDealName());
        setDealType(bidList.getDealType());
        setSourceListId(bidList.getSourceListId());
        setSide(bidList.getSide());
        return this;
    }

}
