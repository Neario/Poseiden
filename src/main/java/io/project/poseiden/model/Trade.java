package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@Accessors(chain=true)
@DynamicUpdate
@Entity
@Table(name = "trade")
public class Trade implements CrudModel<Trade> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @NotNull(message = "must not be null")
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
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

    public Trade update(Trade trade){
        setAccount(trade.getAccount());
        setType(trade.getType());
        setBuyQuantity(trade.getBuyQuantity());
        setSellQuantity(trade.getSellQuantity());
        setBuyPrice(trade.getBuyPrice());
        setSellPrice(trade.getSellPrice());
        setBenchmark(trade.getBenchmark());
        setTradeDate(trade.getTradeDate());
        setSecurity(trade.getSecurity());
        setStatus(trade.getStatus());
        setTrader(trade.getTrader());
        setBook(trade.getBook());
        setCreationName(trade.getCreationName());
        setCreationDate(trade.getCreationDate());
        setRevisionName(trade.getRevisionName());
        setRevisionDate(trade.getRevisionDate());
        setDealName(trade.getDealName());
        setDealType(trade.getDealType());
        setSourceListId(trade.getSourceListId());
        setSide(trade.getSide());
        return this;
    }
}
