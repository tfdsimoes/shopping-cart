package xyz.lana.lanaserver.repository;

import org.springframework.stereotype.Repository;
import xyz.lana.lanaserver.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository {

    /**
     * Search for a product with id in the system
     *
     * @param id {@link String} Id of the product to find
     * @return Optional with the value if exist
     */
    Optional<Product> findProductById(String id);
}
