package com.example.scp.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bonbon")
public class BonbonProperties {

    String one;
    String two;
    String three;
    String custom;

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public String toString() {
        return "BonbonProperties{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", three='" + three + '\'' +
                ", custom='" + custom + '\'' +
                '}';
    }
}
