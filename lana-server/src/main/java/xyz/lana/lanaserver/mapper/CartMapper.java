package xyz.lana.lanaserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.lana.lanaserver.dto.CartDTO;
import xyz.lana.lanaserver.entity.Cart;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

/**
 * Class that handles de conversion from Cart to CartDTO
 */
@Mapper(nullValueCheckStrategy = ALWAYS)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDTO CartToCartDTO (Cart cart);
}
