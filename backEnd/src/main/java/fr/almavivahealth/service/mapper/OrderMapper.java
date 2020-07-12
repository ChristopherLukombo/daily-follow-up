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
 * @version 17
 */
@Mapper(componentModel = "spring", uses = { ContentMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "deliveryDate", target = "deliveryDate")
	@Mapping(source = "orderStatus", target = "orderStatus", qualifiedByName = "toOrderStatus")
	@Mapping(source = "patient.id", target = "patientId")
	@Mapping(source = "entry", target = "entry")
	@Mapping(source = "dish", target = "dish")
	@Mapping(source = "dessert", target = "dessert")
	@Mapping(source = "moment", target = "moment")
	@Mapping(source = "dairyProduct", target = "dairyProduct")
	@Mapping(source = "garnish", target = "garnish")
	@Mapping(source = "createdBy", target = "createdBy")
	@Mapping(source = "createdDate", target = "createdDate")
	@Mapping(source = "lastModifDate", target = "lastModifDate")
	@Mapping(source = "lastModifBy", target = "lastModifBy")
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
