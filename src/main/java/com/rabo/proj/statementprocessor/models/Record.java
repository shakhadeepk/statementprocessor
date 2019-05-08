package com.rabo.proj.statementprocessor.models;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Record {

    @XmlAttribute
    private String reference;
    @XmlElement
    private String accountNumber;
    @XmlElement
    private String description;
    @XmlElement
    private BigDecimal startBalance;
    @XmlElement
    private BigDecimal mutation;
    @XmlElement
    private BigDecimal endBalance;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance.setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getMutation() {
        return mutation;
    }

    public void setMutation(BigDecimal mutation) {
        this.mutation = mutation.setScale(2,RoundingMode.FLOOR);
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance.setScale(2,RoundingMode.FLOOR);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Record{" +
                "reference='" + reference + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", description='" + description + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", endBalance=" + endBalance +
                '}';
    }
}
