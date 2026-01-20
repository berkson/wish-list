package com.berkson.wish.wishlist.aplication.service;

import com.berkson.wish.wishlist.infra.controller.dto.ProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 **/

@Service
@RequiredArgsConstructor
public class RequestService {
    private final WishListService wishListService;

    public Mono<WishListResponse> addProductToWishList(String customerId, String productId) {
        return wishListService.addProductToWishList(customerId, productId)
                .map(w -> new WishListResponse(w.getCustomerId(), w.getProducts()
                        .stream().map(p -> new ProductResponse(p.getProductId())).toList()));
    }

    public Mono<WishListResponse> removeProductFromWishList(String customerId, String productId) {
        return wishListService.removeProductFromWishList(customerId, productId)
                .map(w -> new WishListResponse(w.getCustomerId(), w.getProducts()
                        .stream().map(p -> new ProductResponse(p.getProductId())).toList()));
    }

    public Mono<List<ProductResponse>> getWishListProducts(String customerId) {
        return wishListService.getAllProductsFromWishList(customerId)
                .map(products -> products.stream()
                        .map(p -> new ProductResponse(p.getProductId()))
                        .toList());
    }

    public Mono<Boolean> isProductInWishList(String customerId, String productId) {
        return wishListService.isProductInWishList(customerId, productId);
    }

}
