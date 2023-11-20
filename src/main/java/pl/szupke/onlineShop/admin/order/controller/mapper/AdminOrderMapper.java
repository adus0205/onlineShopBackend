package pl.szupke.onlineShop.admin.order.controller.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.szupke.onlineShop.admin.order.controller.dto.AdminOrderDto;
import pl.szupke.onlineShop.admin.order.model.AdminOrder;

import java.util.List;

public class AdminOrderMapper {

    public static Page<AdminOrderDto> mapToPageDtos(Page<AdminOrder> orders){
        return new PageImpl<AdminOrderDto>(mapToDtoList(orders.getContent()), orders.getPageable(), orders.getTotalElements());
    }

    public static List<AdminOrderDto> mapToDtoList(List<AdminOrder> content){
        return content.stream()
                .map(AdminOrderMapper::mapToAdminOrderDto)
                .toList();
    }

    public static AdminOrderDto mapToAdminOrderDto(AdminOrder adminOrder){
        return AdminOrderDto.builder()
                .id(adminOrder.getId())
                .orderStatus(adminOrder.getOrderStatus())
                .placeDate(adminOrder.getPlaceDate())
                .grossValue(adminOrder.getGrossValue())
                .build();
    }
}
