package rus.cheremisin.itktasksspringmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import rus.cheremisin.itktasksspringmvc.entity.Customer;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.exception.ShoppingOrderListIsNullException;
import rus.cheremisin.itktasksspringmvc.service.CustomerService;
import rus.cheremisin.itktasksspringmvc.util.TestObjectFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerService service;
    private final TestObjectFactory testObjectFactory = new TestObjectFactory();

    @Test
    @DisplayName("addCustomer Должен вернуть Customer cо сгенерированным Id")
    void addCustomer_shouldReturnCustomerWithGeneratedId() {
        Customer validCustomerToAdd = testObjectFactory.getValidCustomer();
        when(service.addCustomer(validCustomerToAdd))
                .thenReturn(new Customer(
                        testObjectFactory.getValidId(),
                        validCustomerToAdd.getFirstName(),
                        validCustomerToAdd.getLastName(),
                        validCustomerToAdd.getEmail()));

        Customer newCustomer = service.addCustomer(testObjectFactory.getValidCustomer());
        Customer customerWithGeneratedId = testObjectFactory.getValidCustomerWithGeneratedId();

        assertThat(newCustomer).isNotNull();
        assertThat(newCustomer.getId()).isNotNull();
        assertThat(newCustomer).isEqualTo(customerWithGeneratedId);

    }

    @Test
    @DisplayName("addCustomer Должен выбросить ShoppingOrderListIsNullException, когда shoppingOrder null")
    void addCustomer_shouldThrowShoppingOrderListIsNullExceptionWhenShoppingOrderIsNull() {
        Customer validCustomerToAdd = testObjectFactory.getValidCustomer();

        when(service.addCustomer(validCustomerToAdd)).thenThrow(ShoppingOrderListIsNullException.class);

        assertThatThrownBy(() -> service.addCustomer(validCustomerToAdd)).isInstanceOf(ShoppingOrderListIsNullException.class);
    }

    @Test
    @DisplayName("addCustomer Должен выбросить NullPointerException, когда Customer null")
    void addCustomer_shouldThrowNullPointerExceptionWhenCustomerIsNull() {

        when(service.addCustomer(null)).thenThrow(ShoppingOrderListIsNullException.class);
        assertThatThrownBy(() -> service.addCustomer(null)).isInstanceOf(ShoppingOrderListIsNullException.class);
    }

    @Test
    @DisplayName("getCustomerById Должен вернуть Customer, когда ID действителен")
    void getCustomerById_shouldReturnCustomer() {
        when(service.getCustomerById(testObjectFactory.getValidId())).thenReturn(testObjectFactory.getValidCustomerWithGeneratedId());
        Customer customer = service.getCustomerById(testObjectFactory.getValidId());
        assertThat(customer).isNotNull();
        assertThat(customer).isEqualTo(testObjectFactory.getValidCustomerWithGeneratedId());
    }


    @Test
    @DisplayName("getCustomerById Должен выбросить CustomerNotFoundException, когда ID недействителен")
    void getCustomerById_shouldThrowCustomerNotFoundExceptionWhenIdIsInvalid() {
        Long invalidId = testObjectFactory.getInvalidId();

        when(service.getCustomerById(invalidId)).thenThrow(CustomerNotFoundException.class);
        assertThatThrownBy(() -> service.getCustomerById(invalidId)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("getCustomerById Должен выбросить CustomerNotFoundException, когда ID равен null")
    void getCustomerById_shouldThrowCustomerNotFoundExceptionWhenIdIsNull() {
        when(service.getCustomerById(null)).thenThrow(CustomerNotFoundException.class);
        assertThatThrownBy(() -> service.getCustomerById(null)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("getAllCustomers Должен вернуть Page of <Customer>")
    void getAllCustomers_shouldReturnListOfCustomers() {
        Pageable pageable = testObjectFactory.getPageable();
        Page<Customer> customerPage = testObjectFactory.getCustomerPage();

        when(service.getAllCustomers(pageable)).thenReturn(customerPage);

        Page<Customer> customers = service.getAllCustomers(pageable);

        assertThat(customers).isNotNull();
        assertThat(customers).isEqualTo(testObjectFactory.getCustomerPage());
    }

    @Test
    @DisplayName("editCustomer Должен вернуть отредактированного Customer")
    void editCustomer_shouldReturnEditedCustomer() {
        Long validId = testObjectFactory.getValidId();
        Customer validCustomerToEdit = testObjectFactory.getValidCustomer2();

        when(service.editCustomer(validId, validCustomerToEdit))
                .thenReturn(testObjectFactory.getValidCustomerWithGeneratedId2());

        Customer editedCustomer = service.editCustomer(validId, testObjectFactory.getValidCustomer2());
        Customer editedCustomerWithGeneratedId2 = testObjectFactory.getValidCustomerWithGeneratedId2();

        assertThat(editedCustomer).isNotNull();
        assertThat(editedCustomer.getId()).isNotNull();
        assertThat(editedCustomer).isEqualTo(editedCustomerWithGeneratedId2);
    }

    @Test
    @DisplayName("editCustomer Должен выбросить CustomerNotFoundException, когда ID недействителен")
    void editCustomer_shouldThrowCustomerNotFoundExceptionWhenIdIsInvalid() {
        Long invalidId = testObjectFactory.getInvalidId();
        Customer validCustomerToEdit = testObjectFactory.getValidCustomer2();

        when(service.editCustomer(invalidId, validCustomerToEdit)).thenThrow(CustomerNotFoundException.class);
        assertThatThrownBy(() -> service.editCustomer(invalidId, validCustomerToEdit)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("editCustomer Должен выбросить CustomerNotFoundException, когда ID null")
    void editCustomer_shouldThrowCustomerNotFoundExceptionWhenIdIsNull() {
        Customer validCustomerToEdit = testObjectFactory.getValidCustomer2();

        when(service.editCustomer(null, validCustomerToEdit)).thenThrow(CustomerNotFoundException.class);
        assertThatThrownBy(() -> service.editCustomer(null, validCustomerToEdit)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("editCustomer Должен выбросить ShoppingOrderListIsNullException, когда shoppingOrder null")
    void editCustomer_shouldThrowShoppingOrderListIsNullExceptionWhenShoppingOrderIsNull() {
        Long validId = testObjectFactory.getValidId();
        Customer validCustomerToEdit = testObjectFactory.getValidCustomer2();
        validCustomerToEdit.setShoppingOrders(null);

        when(service.editCustomer(validId, validCustomerToEdit)).thenThrow(ShoppingOrderListIsNullException.class);
        assertThatThrownBy(() -> service.editCustomer(validId, validCustomerToEdit)).isInstanceOf(ShoppingOrderListIsNullException.class);
    }

    @Test
    @DisplayName("editCustomer Должен выбросить NullPointerException, когда Customer null")
    void editCustomer_shouldThrowNullPointerExceptionWhenCustomerIsNull() {
        Long validId = testObjectFactory.getValidId();

        when(service.editCustomer(validId, null)).thenThrow(ShoppingOrderListIsNullException.class);
        assertThatThrownBy(() -> service.editCustomer(validId, null)).isInstanceOf(ShoppingOrderListIsNullException.class);
    }


    @Test
    @DisplayName("deleteCustomer Должен выбросить CustomerNotFoundException, когда ID недействителен")
    void deleteCustomer_shouldThrowCustomerNotFoundExceptionWhenIdIsNull() {
        Long invalidId = testObjectFactory.getInvalidId();
        doThrow(new CustomerNotFoundException("no customer with such ID")).when(service).deleteCustomerById(invalidId);
        assertThrows(CustomerNotFoundException.class, () -> service.deleteCustomerById(invalidId));
    }

}
