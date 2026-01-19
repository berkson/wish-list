package com.berkson.wish.wishlist.domain.repository;

import com.berkson.wish.wishlist.domain.entity.WishList;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

public interface WishListRepository extends ReactiveCrudRepository<WishList, String> {
    Mono<WishList> findByCustomerId(String customerId);
    Mono<Void> deleteByCustomerId(String customerId);
}
