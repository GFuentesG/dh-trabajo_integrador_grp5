package com.dh.backend.service.implementation;

import com.dh.backend.model.Image;
import com.dh.backend.model.Product;
import com.dh.backend.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    private IProductService productService;
    private Set<Image> imageSet = new HashSet<>();

    @Autowired
    public ProductServiceImplTest(IProductService productService) {
        this.productService = productService;
    }

    @BeforeEach
    void setUp() {
        //productService.deleteAll();
        imageSet.clear();
        imageSet.add(new Image(null, "url1", null));
        imageSet.add(new Image(null, "url2", null));
        imageSet.add(new Image(null, "url3", null));
        imageSet.add(new Image(null, "url4", null));
        imageSet.add(new Image(null, "url5", null));
    }

    @Test
    @DisplayName("Testear que un producto se guarde en la bd con almenos 5 imagenes.")
    void testSaveProduct() throws Exception {
        // arrange
        Product product = new Product(null, "nombre", "descripcion", 11.11d, null, imageSet);

        // act
        Product savedProduct = productService.saveProduct(product);

        // assert
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("nombre", savedProduct.getName());
        assertEquals(11.11d, savedProduct.getPrice());
        assertNotNull(savedProduct.getCreatedAt());
        assert(savedProduct.getImageSet().size() >= 5);
        // -> falta verificar que cada imagen se guarde correctamente
    }

    @Test
    @DisplayName("Testear que un producto no se guarde si existe su nombre en la bd.")
    void testExceptionIfProductNameExists() throws Exception {
        Product product1 = new Product(null, "nombre1", "descripcion1", 11.11d, null, imageSet);
        Product product2 = new Product(null, "nombre1", "descripcion2", 22.22d, null, imageSet);

        Product savedProduct1 = productService.saveProduct(product1);
        Exception ex = assertThrows(
                Exception.class,
                () -> productService.saveProduct(product2)
        );

        assertEquals("El producto con nombre '" + savedProduct1.getName() + "' ya existe.", ex.getMessage());
    }

    @Test
    @DisplayName("Testear que un producto no se guarde en la bd si tiene menos de 5 imagenes.")
    void testExceptionIfFewProductImages() {
        imageSet.clear();
        Product product = new Product(null, "nombre2", "descripcion", 11.11d, null, imageSet);

        Exception ex = assertThrows(
                Exception.class,
                () -> productService.saveProduct(product)
        );

        assertEquals("El producto debe tener almenos 5 imagenes.", ex.getMessage());
    }
}