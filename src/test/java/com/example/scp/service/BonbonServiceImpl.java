package com.example.scp.service;

import com.example.scp.aop.annotation.BonbonAnnotation;
import com.example.scp.service.interfaces.BonbonService;
import org.springframework.stereotype.Service;

@Service
public class BonbonServiceImpl implements BonbonService {

    @Override
    @BonbonAnnotation("halo")
    public void test() {

    }

}
