package com.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Access implements Serializable {

    private Integer user_id;
    private String date;
    private AccessType type;

    @Override
    public String toString() {
        return date + " - " + type.getName();
    }

}
