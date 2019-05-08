package com.rabo.proj.statementprocessor.services;

import com.rabo.proj.statementprocessor.models.Record;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecordValidationAndReportGeneration {

    private static Map<String,String> references = new HashMap<>();

    public String getReferences(String references) {
        return this.references.get(references);
    }

    public void setReferences(String references,String description) {
        this.references.put(references,description);
    }

    public boolean validateReferenceNumber(Record record){
        if(this.getReferences(record.getReference())!=null){
            return false;
        }
        this.setReferences(record.getReference(),record.getDescription());
        return true;
    }
    public boolean validateEndBalance(Record record){
        BigDecimal sum= record.getStartBalance().add(record.getMutation());
        sum=sum.setScale(2, RoundingMode.FLOOR);
        if(sum.compareTo(record.getEndBalance())!=0){
            return false;
        }
        return true;
    }
    public boolean validateRecord(Record record){
        return (validateReferenceNumber(record) && validateEndBalance(record));
    }
}
