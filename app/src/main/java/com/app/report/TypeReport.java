package com.app.report;

import com.app.model.AccessType;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeReport implements Serializable {

    private AccessType type;
    private Integer quantity;

}
