package com.berkson.wish.wishlist.infra.controller;

import com.berkson.wish.wishlist.aplication.service.RequestService;
import com.berkson.wish.wishlist.infra.controller.dto.AddProductRequest;
import com.berkson.wish.wishlist.infra.controller.dto.CheckProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.ProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 * Devido ao tempo limitado, optei por não implementar um {@link RestControllerAdvice} para tratamento global de exceções.
 **/

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishListController {
    private final RequestService requestService;

    @PostMapping("/{customerId}/produtos")
    public Mono<ResponseEntity<WishListResponse>> addProduct(@PathVariable String customerId,
                                                             @RequestBody AddProductRequest request) {
        return requestService.addProductToWishList(customerId, request.productId())
                .map(wishListResponse ->
                        ResponseEntity.status(HttpStatus.CREATED).body(wishListResponse))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()))
                .onErrorResume(IllegalStateException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @DeleteMapping("/{customerId}/produtos/{productId}")
    public Mono<ResponseEntity<WishListResponse>> removeProduct(@PathVariable String customerId,
                                                                @PathVariable String productId) {
        return requestService.removeProductFromWishList(customerId, productId)
                .map(wishListResponse ->
                        ResponseEntity.status(HttpStatus.OK).body(wishListResponse))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()))
                .onErrorResume(IllegalStateException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/{customerId}/produtos")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<ProductResponse>> getProducts(@PathVariable String customerId) {
        return requestService.getWishListProducts(customerId);
    }

    @GetMapping("/{customerId}/produtos/{productId}/exists")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CheckProductResponse> isProductInWishList(@PathVariable String customerId,
                                                          @PathVariable String productId) {
        return requestService.isProductInWishList(customerId, productId);
    }
}
