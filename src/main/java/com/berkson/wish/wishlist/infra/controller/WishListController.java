package com.berkson.wish.wishlist.infra.controller;

import com.berkson.wish.wishlist.aplication.service.RequestService;
import com.berkson.wish.wishlist.infra.controller.dto.AddProductRequest;
import com.berkson.wish.wishlist.infra.controller.dto.CheckProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.ProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 **/

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishListController {
    private final RequestService requestService;

    @PostMapping("/{customerId}/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WishListResponse> addProduct(@PathVariable String customerId,
                                             @RequestBody AddProductRequest request) {
        return requestService.addProductToWishList(customerId, request.productId());
    }

    @DeleteMapping("/{customerId}/produtos/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<WishListResponse> removeProduct(@PathVariable String customerId,
                                                @PathVariable String productId) {
        return requestService.removeProductFromWishList(customerId, productId);
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
