package com.berkson.wish.wishlist.domain.repository;

import com.berkson.wish.wishlist.domain.entity.WishList;
import reactor.core.publisher.Mono;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 **/

public interface WishListRepository {
    Mono<WishList> findByCustomerId(String customerId);

    Mono<WishList> save(WishList wishlist);

    Mono<Void> deleteByCustomerId(String customerId);
}
