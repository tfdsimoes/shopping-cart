package xyz.lana.lanaserver.repository.impl;

import org.springframework.stereotype.Repository;
import xyz.lana.lanaserver.entity.Promotion;
import xyz.lana.lanaserver.repository.InformationRepository;
import xyz.lana.lanaserver.repository.PromotionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles requests about Promotions in "db" memory
 */
@Repository
public class PromotionRepositoryImpl implements PromotionRepository {

    private InformationRepository informationRepository;

    public PromotionRepositoryImpl(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }


    /**
     * Search for the promotions available for a product
     *
     * @param productId {@link String} Product id to search in the promotions
     * @return List of available promotions
     */
    @Override
    public List<Promotion> findPromotionsByProductId(String productId) {
        List<Promotion> promotions = informationRepository.getPromotions();
        List<Promotion> promotionsProduct = new ArrayList<>();

        for (Promotion promotion : promotions) {
            if (promotion.getProductId().equals(productId)) {
                promotionsProduct.add(promotion);
            }
        }
        return promotionsProduct;
    }
}
