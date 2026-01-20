package com.berkson.wish.wishlist.infra.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Schema(description = "Verifica se há um produto na lista de desejos")
public record CheckProductResponse(
        @Schema(description = "Se existe o produto na lista de desejos", example = "true")
        boolean exists) {
}
