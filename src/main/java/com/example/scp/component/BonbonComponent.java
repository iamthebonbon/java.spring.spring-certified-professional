package com.example.scp.component;

import com.example.sibling.SiblingBonbonComponent;
import org.springframework.stereotype.Component;

@Component
public class BonbonComponent {

    private final SiblingBonbonComponent siblingBonbonComponent;

    public BonbonComponent(SiblingBonbonComponent siblingBonbonComponent) {
        this.siblingBonbonComponent = siblingBonbonComponent;
    }

    public String comCheck() {
        siblingBonbonComponent.run();
        return "Houston";
    }

}
