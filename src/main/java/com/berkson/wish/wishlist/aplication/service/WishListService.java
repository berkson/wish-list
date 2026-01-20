package com.berkson.wish.wishlist.aplication.service;

import com.berkson.wish.wishlist.domain.entity.Product;
import com.berkson.wish.wishlist.domain.entity.WishList;
import com.berkson.wish.wishlist.domain.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 *
 * No contexto desta aplicação (desafio) não se faz necessário o uso de transações por isso
 * não utilizei a anotação @Transactional nos métodos. Ainda sim coloquei para no caso de uso em
 * modo Replica Set - MongoDB, onde as transações são suportadas.
 **/

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public Mono<WishList> addProductToWishList(String customerId, String productId) {
        return repository.findByCustomerId(customerId)
                .defaultIfEmpty(WishList.create(customerId))
                .flatMap(wishList -> {
                    wishList.addProduct(Product.create(productId));
                    return repository.save(wishList);
                });
    }

    public Mono<Boolean> isProductInWishList(String customerId, String productId) {
        return repository.findByCustomerId(customerId)
                .map(wishList -> wishList.containsProduct(productId))
                .defaultIfEmpty(false);
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<WishList> removeProductFromWishList(String customerId, String productId) {
        return repository.findByCustomerId(customerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Lista de desejos do cliente " +
                                                                       "customerId não foi encontrada: " + customerId)))
                .flatMap(wishList -> {
                    wishList.removeProduct(productId);
                    return repository.save(wishList);
                });
    }

    public Mono<List<Product>> getAllProductsFromWishList(String customerId) {
        return repository.findByCustomerId(customerId)
                .map(WishList::getAllProducts)
                .defaultIfEmpty(List.of());
    }

}
