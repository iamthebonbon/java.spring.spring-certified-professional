package com.example.scp.actuator;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

@Component
@WebEndpoint(id = "bonbon")
public class BonbonActuator {

    @ReadOperation
    public String get() {
        return "Halo, houston";
    }

}
