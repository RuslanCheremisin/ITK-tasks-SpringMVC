package rus.cheremisin.itktasksspringmvc.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.entity.Product;
import rus.cheremisin.itktasksspringmvc.service.ProductService;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.util.List;

@RestController
@Validated
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @Qualifier("productMapper")
    private final RequestMapper<Product> requestMapper;

    @Autowired
    public ProductController(ProductService productService, RequestMapper<Product> requestMapper) {
        this.productService = productService;
        this.requestMapper = requestMapper;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getProductById(@PathVariable("id") @Min(1) Long id) {
        Product product = productService.getProductById(id);
        byte[] json = requestMapper.toJsonFile(product);
        StringBuilder sb = new StringBuilder("attachment; filename=product_")
                .append(product.getProductId())
                .append(".json");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, sb.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> addProduct(@RequestParam("file") MultipartFile productFile) {
        Product product = requestMapper.toEntity(productFile);
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable("id") @Min(1) Long id,
                                               @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.editProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") @Min(1) Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }


}
