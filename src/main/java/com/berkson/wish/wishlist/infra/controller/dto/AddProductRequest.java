package com.berkson.wish.wishlist.infra.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Schema(description = "Adiciona um produto à lista de desejos")
public record AddProductRequest(
        @Schema(description = "Id do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        String productId) {
}
