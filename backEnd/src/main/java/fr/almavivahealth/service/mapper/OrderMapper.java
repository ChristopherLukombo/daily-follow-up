package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.enums.OrderStatus;
import fr.almavivahealth.service.dto.OrderDTO;

/**
 * Mapper for the entity Order and its DTO called OrderDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", uses = { ContentMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "deliveryDate", target = "deliveryDate")
	@Mapping(source = "orderStatus", target = "orderStatus", qualifiedByName = "toOrderStatus")
	@Mapping(source = "patient.id", target = "patientId")
	@Mapping(source = "entries", target = "entries")
	@Mapping(source = "dishes", target = "dishes")
	@Mapping(source = "desserts", target = "desserts")
	@Mapping(source = "moment", target = "moment")
	@Mapping(source = "dairyProducts", target = "dairyProducts")
	OrderDTO orderToOrderDTO(Order order);

	@InheritInverseConfiguration
	Order orderDTOToOrder(OrderDTO orderDTO);

	@Named("toOrderStatus")
	default String toOrderStatus(final Enum<OrderStatus> orderStatus) {
		if (orderStatus == null) {
			return null;
		}
		return orderStatus.toString();
	}
}
