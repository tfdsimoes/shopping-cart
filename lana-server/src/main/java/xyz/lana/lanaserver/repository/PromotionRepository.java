package xyz.lana.lanaserver.repository;

import org.springframework.stereotype.Repository;
import xyz.lana.lanaserver.entity.Promotion;

import java.util.List;

public interface PromotionRepository {

    /**
     * Search for the promotions available for a product
     *
     * @param productId {@link String} Product id to search in the promotions
     * @return List of available promotions
     */
    List<Promotion> findPromotionsByProductId(String productId);
}
