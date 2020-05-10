package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.service.dto.OrderDTO;

/**
 * Mapper for the entity Order and its DTO called OrderDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "date", target = "date")
	@Mapping(source = "patient.id", target = "patientId")
	OrderDTO orderToOrderDTO(Order order);

	@InheritInverseConfiguration
	Order orderDTOToOrder(OrderDTO orderDTO);
}
