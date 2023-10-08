package com.springframework.spring6reactive.controller;

import com.springframework.spring6reactive.model.CustomerDTO;
import com.springframework.spring6reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_URL = "/api/v2/customer";
    public static final String CUSTOMER_URL_ID = CUSTOMER_URL + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_URL)
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @PostMapping(CUSTOMER_URL)
    Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.saveNewCustomer(customerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + CUSTOMER_URL
                                        + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @GetMapping(CUSTOMER_URL_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    @DeleteMapping(CUSTOMER_URL_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("customerId") Integer customerId) {
        return customerService.deleteCustomerById(customerId).map(response -> ResponseEntity
                .noContent().build());
    }

    @PatchMapping(CUSTOMER_URL_ID)
    Mono<ResponseEntity<Void>> patchExistingCustomer(@PathVariable("customerId") Integer customerId,
                                                     @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(customerId, customerDTO)
                .map(updatedDto -> ResponseEntity.ok().build());
    }

    @PutMapping(CUSTOMER_URL_ID)
    Mono<ResponseEntity<Void>> updateExistingCustomer(@PathVariable("customerId") Integer customerId,
                                                      @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerId, customerDTO)
                .map(savedDto -> ResponseEntity.ok().build());
    }
}
