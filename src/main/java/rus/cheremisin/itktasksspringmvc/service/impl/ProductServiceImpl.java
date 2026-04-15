package rus.cheremisin.itktasksspringmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringmvc.dao.ProductDAO;
import rus.cheremisin.itktasksspringmvc.entity.Product;
import rus.cheremisin.itktasksspringmvc.exception.ProductNotFoundException;
import rus.cheremisin.itktasksspringmvc.service.ProductService;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("pageable is null!");
        }
        return productDAO.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productDAO.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("no product with such ID");
        }
        return productOptional.get();
    }

    @Override
    public Product addProduct(Product product) {
        if (product == null) {
            throw new NullPointerException("cannot add null product");
        }
        return productDAO.save(product);
    }


    @Override
    public Product editProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = productDAO.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("no product with such ID");
        }
        Product existingProduct = productOptional.get();
        if (updatedProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());

            return productDAO.save(existingProduct);
        } else {
            throw new NullPointerException("cannot update from null product");
        }
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> productOptional = productDAO.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("no product with such ID");
        }
        productDAO.delete(productOptional.get());
    }
}
