package com.app.report;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReport implements Serializable {

    private Integer id;
    private String name;
    private Integer quantity;

}
