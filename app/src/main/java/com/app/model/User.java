package com.app.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String tag;
    private UserStatus status;

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", id, name, status.getName());
    }

}
