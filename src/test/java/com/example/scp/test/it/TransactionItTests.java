package com.example.scp.test.it;

import com.example.scp.service.TransactionPropagationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;

class TransactionItTests extends AbstractItConfiguration {

    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @Test
    @Transactional
    public void never() {
        var exception = Assertions.assertThrows(IllegalTransactionStateException.class, () -> {
            transactionPropagationService.never();
        });
        Assertions.assertEquals(
                "Existing transaction found for transaction marked with propagation 'never'",
                exception.getMessage()
        );
    }

    @Test
    public void mandatory() {
        var exception = Assertions.assertThrows(IllegalTransactionStateException.class, () -> {
            transactionPropagationService.mandatory();
        });
        Assertions.assertEquals(
                "No existing transaction found for transaction marked with propagation 'mandatory'",
                exception.getMessage()
        );
    }

}
