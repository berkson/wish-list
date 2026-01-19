package com.berkson.wish.wishlist.data.repository;

import com.berkson.wish.wishlist.data.domain.WishListDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

public interface WishListRepository extends ReactiveMongoRepository<WishListDocument, String> {
    Mono<WishListDocument> findByCustomerId(String customerId);

    Mono<Void> deleteByCustomerId(String customerId);
}
