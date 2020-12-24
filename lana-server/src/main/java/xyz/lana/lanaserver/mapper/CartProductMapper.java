package xyz.lana.lanaserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.lana.lanaserver.controller.dto.CartProductDTO;
import xyz.lana.lanaserver.entity.CartProduct;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(nullValueCheckStrategy = ALWAYS)
public interface CartProductMapper {

    CartProductMapper INSTANCE = Mappers.getMapper(CartProductMapper.class);

    CartProductDTO CartProductToCartProductDTO (CartProduct cartProduct);
}
