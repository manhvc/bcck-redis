package vn.edu.uit.ms.qltt.cuoiky.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
            Integer id;
    String userId;
    BigDecimal amount;
    BigDecimal fee;
    BigDecimal tax;
    String productId;
    Date createdAt;
    Date modifiedAt;

    public Integer getId() {
        return id;
    }

    public Transaction setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Transaction setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Transaction setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public Transaction setTax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public Transaction setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Transaction setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Transaction setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }
}
