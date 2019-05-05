package com.rabo.proj.statementprocessor.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Record {

    @XmlAttribute
    private String reference;

    @XmlElement
    private String accountNumber;

    @XmlElement
    private Double startBalance;

    @XmlElement
    private Double mutation;

    @XmlElement
    private Double endBalance;

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

    public Double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getMutation() {
        return mutation;
    }

    public void setMutation(Double mutation) {
        this.mutation = mutation;
    }

    public Double getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Double endBalance) {
        this.endBalance = endBalance;
    }

    @Override
    public String toString() {
        return "Record{" +
                "reference='" + reference + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", endBalance=" + endBalance +
                '}';
    }
}
