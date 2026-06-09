package io.project.poseiden.model;

import jakarta.persistence.*;
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
    private String account;
    private String type;
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
        setBid(bidList.getBid());
        setAsk(bidList.getAsk());

        return this;
    }

}
