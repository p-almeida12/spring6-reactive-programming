package com.springframework.spring6reactive.controller;

import com.springframework.spring6reactive.model.BeerDTO;
import com.springframework.spring6reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_URL = "api/v2/beer";
    public static final String BEER_URL_ID = BEER_URL + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_URL)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping(BEER_URL_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId){
        return beerService.getBeerById(beerId);
    }

    @PostMapping(BEER_URL)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO){
        return beerService.saveNewBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + BEER_URL
                                        + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(BEER_URL_ID)
    ResponseEntity<Object> updateBeer(@PathVariable("beerId") Integer beerId, @Validated @RequestBody BeerDTO beerDTO){
        beerService.updateBeer(beerId, beerDTO).subscribe();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(BEER_URL_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer beerId){
        return beerService.deleteBeerById(beerId).map(response -> ResponseEntity
                .noContent().build());
    }

    @PatchMapping(BEER_URL_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(@PathVariable Integer beerId,
                                                 @Validated @RequestBody BeerDTO beerDTO){
        return beerService.patchBeer(beerId, beerDTO)
                .map(updatedDto -> ResponseEntity.ok().build());
    }

}
