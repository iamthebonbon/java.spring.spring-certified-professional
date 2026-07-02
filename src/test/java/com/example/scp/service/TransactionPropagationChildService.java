package com.example.scp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionPropagationChildService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requireNew(Runnable runnable) {
        runnable.run();
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requireNewException(Runnable runnable) {
        runnable.run();
    }

}
