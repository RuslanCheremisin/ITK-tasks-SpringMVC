package rus.cheremisin.itktasksspringmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rus.cheremisin.itktasksspringmvc.controller.CustomerController;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;
import rus.cheremisin.itktasksspringmvc.service.RequestMapper;
import rus.cheremisin.itktasksspringmvc.util.TestObjectFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockitoBean
    private CustomerService service;
    @MockitoBean
    private RequestMapper<Customer> requestMapper;
    private final TestObjectFactory testObjectFactory = new TestObjectFactory();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getAllCustomers должен вернуть 200 и в теле список Customer с полями согласно JsonView - CustomerSummary")
    void getAllCustomers_shouldReturnPageOfCustomers() throws Exception {
        Page<Customer> customerPage = testObjectFactory.getCustomerPage();

        when(service.getAllCustomers(any())).thenReturn(customerPage);
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ivan"))
                .andExpect(jsonPath("$[0].lastName").value("Ivanov"))
                .andExpect(jsonPath("$[0].email").value("ivan@ivanov.net"))
                .andExpect(jsonPath("$[0].contactNumber").value("+7 999 123 45 67"))
                .andExpect(jsonPath("$[0].customerId").doesNotExist())
                .andExpect(jsonPath("$[1].firstName").value("Stepan"))
                .andExpect(jsonPath("$[1].lastName").value("Stepanov"))
                .andExpect(jsonPath("$[1].email").value("stepan@stepanov.net"))
                .andExpect(jsonPath("$[1].contactNumber").value("+7 935 098 76 54"))
                .andExpect(jsonPath("$[1].customerId").doesNotExist());
    }

//    @Test
//    @DisplayName("getCustomerById должен вернуть 200 и в теле Customer с полями согласно JsonView - CustomerDetails")
//    void getCustomerById_withValidIdShouldReturnCustomer() throws Exception {
//        Long validId =  testObjectFactory.getValidId();
//        when(service.getCustomerById(validId)).thenReturn(testObjectFactory.getValidCustomerWithGeneratedId());
//        mockMvc.perform(get("/customers/" + validId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.customerId").value("1"))
//                .andExpect(jsonPath("$.firstName").value("Ivan"))
//                .andExpect(jsonPath("$.lastName").value("Ivanov"))
//                .andExpect(jsonPath("$.email").value("ivan@ivanov.net"))
//                .andExpect(jsonPath("$.contactNumber").value("+7 999 123 45 67"));
//    }

    @Test
    @DisplayName("getCustomerById должен вернуть 400 при id < 1")
    void getCustomerById_withInvalidId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/customers/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getCustomerById должен вернуть 400 при несуществующем id")
    void getCustomerById_withNonExistingId_shouldReturnBadRequest() throws Exception {
        Long nonExistingId = testObjectFactory.getInvalidId();
        when(service.getCustomerById(nonExistingId)).thenThrow(new CustomerNotFoundException("no customer with such ID"));
        mockMvc.perform(get("/customers/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getCustomerById должен вернуть 400 при id = null")
    void getCustomerById_withNullId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/customers/null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @DisplayName("addCustomer должен вернуть 200 и в теле Customer с полями согласно JsonView - CustomerSummary")
//    void addCustomer_shouldReturnCustomerSummary() throws Exception {
//        Customer inputCustomer = testObjectFactory.getValidCustomer();
//        Customer savedCustomer = testObjectFactory.getValidCustomerWithGeneratedId();
//
//        when(service.addCustomer(any(Customer.class))).thenReturn(savedCustomer);
//
//        mockMvc.perform(post("/customers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getObjectMapper().writeValueAsString(inputCustomer)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Ivan"))
//                .andExpect(jsonPath("$.lastName").value("Ivanov"))
//                .andExpect(jsonPath("$.email").value("ivan@ivanov.net"))
//                .andExpect(jsonPath("$.contactNumber").value("+7 999 123 45 67"))
//                .andExpect(jsonPath("$.customerId").doesNotExist());
//    }
//
//    @Test
//    @DisplayName("addCustomer должен вернуть 400 при пустом теле запроса")
//    void addCustomer_withEmptyBody_shouldReturnBadRequest() throws Exception {
//        mockMvc.perform(post("/customers")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("addCustomer должен вернуть 400 при невалидном email")
//    void addCustomer_withInvalidEmail_shouldReturnBadRequest() throws Exception {
//        Customer invalidCustomer = testObjectFactory.getValidCustomer();
//        invalidCustomer.setEmail("wrong format email");
//
//        mockMvc.perform(post("/customers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getObjectMapper().writeValueAsString(invalidCustomer)))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    @DisplayName("editCustomer должен вернуть 400 при id < 1")
    void editCustomer_withInvalidId_shouldReturnBadRequest() throws Exception {
        Customer customer = testObjectFactory.getValidCustomer();

        mockMvc.perform(put("/customers/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("editCustomer должен вернуть 400 при пустом теле запроса")
    void editCustomer_withEmptyBody_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("editCustomer должен вернуть 400 если customer = null")
    void editCustomer_withNullCustomer_shouldReturnBadRequest() throws Exception {

        when(service.editCustomer(any(), any()))
                .thenThrow(new NullPointerException("cannot update from null customer"));

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("editCustomer должен вернуть 404 если customer не найден")
    void editCustomer_whenCustomerNotFound_shouldReturnNotFound() throws Exception {
        Customer customer = testObjectFactory.getValidCustomer();

        when(service.editCustomer(any(), any()))
                .thenThrow(new CustomerNotFoundException("no customer with such ID"));

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("editCustomer должен вернуть 400 при невалидном email")
    void editCustomer_withInvalidEmail_shouldReturnBadRequest() throws Exception {
        Customer customer = testObjectFactory.getValidCustomer();
        customer.setEmail("invalid");

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("editCustomer должен вернуть 400 при невалидном contactNumber")
    void editCustomer_withInvalidContactNumber_shouldReturnBadRequest() throws Exception {
        Customer customer = testObjectFactory.getValidCustomer();
        customer.setContactNumber("invalid");

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Test
    @DisplayName("deleteCustomer должен вернуть 400 при id < 1")
    void deleteCustomer_withInvalidId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/customers/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deleteCustomer должен вернуть 404 если customer не найден")
    void deleteCustomer_whenCustomerNotFound_shouldReturnNotFound() throws Exception {

        doThrow(new CustomerNotFoundException("no customer with such ID"))
                .when(service).deleteCustomerById(any());

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNotFound());
    }

}
