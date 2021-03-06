package com.study.demoProject.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdererInfoDto {
    private Long ordererId;
    private String name;
    private String phone;
}
