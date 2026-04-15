package rus.cheremisin.itktasksspringmvc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.exception.JsonToObjectMappingException;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;

import java.io.File;
import java.io.IOException;

@Service
@Qualifier("customerMapper")
public class RequestMapperCustomerImpl implements RequestMapper<Customer> {

    private final ObjectMapper mapper;

    @Autowired
    public RequestMapperCustomerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Customer toEntity(MultipartFile multipartFile) {
        File file = new File("src/main/resources/customers/inbound/inbound_customer_" + multipartFile.getOriginalFilename());

        try {
            byte[] json = multipartFile.getBytes();
            Customer customer = mapper.readValue(json, Customer.class);
            customer.setId(null);
            file.createNewFile();
            mapper.writeValue(file, customer);
            return customer;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("error while mapping ").append(file.getName()).append(" to Customer object");
            throw new JsonToObjectMappingException(sb.toString());
        }
    }

    @Override
    public byte[] toJsonFile(Customer customer) {
        byte[] json;
        try {
            json = mapper.writeValueAsBytes(customer);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("error while mapping Customer object ").append(customer);
            throw new JsonToObjectMappingException(sb.toString());
        }
        return json;
    }
}

