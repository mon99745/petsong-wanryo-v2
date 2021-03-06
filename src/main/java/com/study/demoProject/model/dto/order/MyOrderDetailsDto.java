package com.study.demoProject.model.dto.order;

import com.study.demoProject.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class MyOrderDetailsDto {
    private LocalDateTime orderDate;
    private Long orderId;
    private List<MyOrderDetailsItemDto> orderedItemList;
    private MyOrderDetailsReceiverInfoDto receiverInfoDto;
    private OrderStatus orderStatus;
}
