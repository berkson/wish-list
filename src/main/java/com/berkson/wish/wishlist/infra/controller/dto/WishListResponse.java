package com.berkson.wish.wishlist.infra.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Schema(description = "Lista de desejos do cliente")
public record WishListResponse(
        @Schema(description = "Id do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
        String customerId,
        @Schema(description = "Produtos na lista de desejos")
        List<ProductResponse> products) {
}
