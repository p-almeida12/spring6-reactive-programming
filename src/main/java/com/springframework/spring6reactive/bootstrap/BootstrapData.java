package com.springframework.spring6reactive.bootstrap;

import com.springframework.spring6reactive.domain.Beer;
import com.springframework.spring6reactive.domain.Customer;
import com.springframework.spring6reactive.repository.BeerRepository;
import com.springframework.spring6reactive.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        beerRepository.count().subscribe(count -> {
            System.out.println("Beer Count is: " + count);
        });

        customerRepository.count().subscribe(count -> {
            System.out.println("Customer Count is: " + count);
        });
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("Sierra Nevada")
                        .beerStyle("PALE_ALE")
                        .upc("45113432")
                        .price(new BigDecimal("12.90"))
                        .quantityOnHand(234)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Super Bock")
                        .beerStyle("PILSNER")
                        .upc("123123")
                        .price(new BigDecimal("1.20"))
                        .quantityOnHand(123)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Guinness")
                        .beerStyle("IPA")
                        .upc("123123")
                        .price(new BigDecimal("11.50"))
                        .quantityOnHand(123)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3)).subscribe();
            }
        });
    }

    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {
            if(count == 0){
                customerRepository.save(Customer.builder()
                                .customerName("Customer 1")
                                .build())
                        .subscribe();

                customerRepository.save(Customer.builder()
                                .customerName("Customer 2")
                                .build())
                        .subscribe();

                customerRepository.save(Customer.builder()
                                .customerName("Customer 3")
                                .build())
                        .subscribe();
            }
        });
    }

}
