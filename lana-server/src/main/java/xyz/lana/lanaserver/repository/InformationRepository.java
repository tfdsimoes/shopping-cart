package xyz.lana.lanaserver.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.lana.lanaserver.entity.Product;
import xyz.lana.lanaserver.entity.Promotion;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try (InputStream inputStream = InformationRepository.class.getResourceAsStream(productsPath)) {

            String contentFile = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining("\n"));
            products = objectMapper.readValue(contentFile, new TypeReference<>() {
            });
        }

        try (InputStream inputStream = InformationRepository.class.getResourceAsStream(promotionsPath)) {
            // Load promotions to memory
            String contentFile = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining("\n"));
            promotions = objectMapper.readValue(contentFile, new TypeReference<>() {
            });
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
