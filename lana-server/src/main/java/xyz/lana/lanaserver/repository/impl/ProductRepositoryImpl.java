package xyz.lana.lanaserver.repository.impl;

import org.springframework.stereotype.Repository;
import xyz.lana.lanaserver.entity.Product;
import xyz.lana.lanaserver.repository.InformationRepository;
import xyz.lana.lanaserver.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Class that handles requests about Products in "db" memory
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private InformationRepository informationRepository;

    public ProductRepositoryImpl(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    /**
     * Search for a product with id in the system
     *
     * @param id {@link String} Id of the product to find
     * @return Optional with the value if exist
     */
    @Override
    public Optional<Product> findProductById(String id) {
        List<Product> products = informationRepository.getProducts();

        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }
}
