package com.example.scp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionPropagationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionPropagationService.class);

    private final TransactionPropagationChildService childService;

    public TransactionPropagationService(TransactionPropagationChildService childService) {
        this.childService = childService;
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {

    }

    @Transactional
    public void requiredWithRequiredNewWithFullRollback(Runnable parentRunnable, Runnable runnable) {
        childService.requireNew(runnable);
    }

    @Transactional
    public void requiredWithRequiredNewWithChildRollback(Runnable parentRunnable, Runnable runnable) {
        try {
            childService.requireNewException(runnable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
