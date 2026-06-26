package com.example.scp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionPropagationService {

    @Transactional(propagation = Propagation.NEVER)
    public void never() {

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {

    }

}
