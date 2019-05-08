package com.rabo.proj.statementprocessor.services;

import com.rabo.proj.statementprocessor.models.Record;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecordValidationAndReportGenerationTest {

    @Autowired
    private RecordValidationAndReportGeneration reportGeneration;

    @Test
    void validateReferenceNumber() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        System.out.println(reportGeneration.validateReferenceNumber(record));

    }

    @Test
    void validateEndBalance() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        System.out.println(reportGeneration.validateEndBalance(record));
    }
}