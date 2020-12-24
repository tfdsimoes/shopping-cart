package xyz.lana.lanaserver.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import xyz.lana.lanaserver.entity.Product;
import xyz.lana.lanaserver.entity.Promotion;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the load of file to memory with Products and Promotions
 */
@Component
public class InformationRepository {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${data.promotions}")
    private String promotionsPath;

    @Value("${data.products}")
    private String productsPath;

    private List<Product> products = new ArrayList<>();

    private List<Promotion> promotions = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        // Load products to memory
        File productsFile = new ClassPathResource(productsPath).getFile();
        products = objectMapper.readValue(productsFile, new TypeReference<>() {
        });

        // Load promotions to memory
        File promotionsFile = new ClassPathResource(promotionsPath).getFile();
        promotions = objectMapper.readValue(promotionsFile, new TypeReference<>() {
        });
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
