package rus.cheremisin.itktasksspringmvc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.entity.Product;
import rus.cheremisin.itktasksspringmvc.exception.JsonToObjectMappingException;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.io.File;
import java.io.IOException;

/**
 * Реализация {@link RequestMapper} для сущности {@link rus.cheremisin.itktasksspringmvc.entity.Product}.
 *
 * <p>Отвечает за:</p>
 * <ul>
 *     <li>Десериализацию JSON-файла в объект Product</li>
 *     <li>Сериализацию объекта Product в JSON</li>
 *     <li>Сохранение входящих JSON-файлов в файловую систему (inbound)</li>
 * </ul>
 *
 * <p>Использует {@link com.fasterxml.jackson.databind.ObjectMapper}
 * для преобразования данных.</p>
 *
 * Для внедрения конкретной реализации {@link RequestMapper} используется
 * аннотация {@link org.springframework.beans.factory.annotation.Qualifier},
 * так как в приложении может существовать несколько мапперов для разных сущностей.
 */

@Service
@Qualifier("productMapper")
public class RequestMapperProductImpl implements RequestMapper<Product> {

    private final ObjectMapper mapper;

    @Autowired
    public RequestMapperProductImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Product toEntity(MultipartFile multipartFile) {
        File file = new File("src/main/resources/products/inbound/inbound_product_" + multipartFile.getOriginalFilename());

        try {
            byte[] json = multipartFile.getBytes();
            Product product = mapper.readValue(json, Product.class);
            file.createNewFile();
            mapper.writeValue(file, product);
            return product;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("error while mapping ").append(file.getName()).append(" to Product object");
            throw new JsonToObjectMappingException(sb.toString());
        }
    }

    @Override
    public byte[] toJsonFile(Product product) {
        byte[] json;
        try {
            json = mapper.writeValueAsBytes(product);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("error while mapping Customer object ").append(product);
            throw new JsonToObjectMappingException(sb.toString());
        }
        return json;
    }
}

