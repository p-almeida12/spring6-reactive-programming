package com.springframework.spring6reactive.mappers;

import com.springframework.spring6reactive.domain.Beer;
import com.springframework.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);

}
