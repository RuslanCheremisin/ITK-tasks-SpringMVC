package rus.cheremisin.itktasksspringmvc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.entity.Order;
import rus.cheremisin.itktasksspringmvc.exception.JsonToObjectMappingException;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.io.File;
import java.io.IOException;

@Service
@Qualifier("orderMapper")
public class RequestMapperOrderImpl implements RequestMapper<Order> {

    private final ObjectMapper mapper;

    @Autowired
    public RequestMapperOrderImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Order toEntity(MultipartFile multipartFile) {
        File file = new File("src/main/resources/orders/inbound/inbound_order_" + multipartFile.getOriginalFilename());

        try {
            byte[] json = multipartFile.getBytes();
            Order order = mapper.readValue(json, Order.class);
            order.setOrderId(null);
            file.createNewFile();
            mapper.writeValue(file, order);
            return order;
        } catch (IOException e) {
            String sb = "error while mapping " + file.getName() + " to Order object";
            throw new JsonToObjectMappingException(sb);
        }
    }

    @Override
    public byte[] toJsonFile(Order order) {
        byte[] json;
        try {
            json = mapper.writeValueAsBytes(order);
        } catch (IOException e) {
            throw new JsonToObjectMappingException("error while mapping Order object " + order);
        }
        return json;
    }
}

