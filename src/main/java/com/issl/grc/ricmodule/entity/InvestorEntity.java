package com.issl.grc.ricmodule.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestorEntity {
 private String id;
 private String name;
 private Integer rowId;
}
