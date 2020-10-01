package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessType {

    C("Cartão"),
    S("Senha");

    private String name;

    public void setName(String name) {
        this.name = name;
    }

}
