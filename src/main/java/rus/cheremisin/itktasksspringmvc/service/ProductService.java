package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rus.cheremisin.itktasksspringmvc.entity.Product;

public interface ProductService {
    Product getProductById(Long id);

    Product addProduct(Product product);

    Product editProduct(Long id, Product product);

    void deleteProductById(Long id);

    Page<Product> getAllProducts(Pageable pageable);
}
