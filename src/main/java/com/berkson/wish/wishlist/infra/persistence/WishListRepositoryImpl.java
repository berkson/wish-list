package com.berkson.wish.wishlist.infra.persistence;

import com.berkson.wish.wishlist.domain.entity.WishList;
import com.berkson.wish.wishlist.domain.repository.WishListRepository;
import com.berkson.wish.wishlist.infra.persistence.mapper.WishListMapper;
import com.berkson.wish.wishlist.infra.persistence.repository.MongoWishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 **/

@Repository
@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepository {
    private final MongoWishListRepository mongoRepository;
    private final WishListMapper mapper;


    @Override
    public Mono<WishList> findByCustomerId(String customerId) {
        return mongoRepository.findByCustomerId(customerId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<WishList> save(WishList wishlist) {
        return mongoRepository.save(mapper.toDocument(wishlist))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByCustomerId(String customerId) {
        return mongoRepository.deleteByCustomerId(customerId);
    }
}
