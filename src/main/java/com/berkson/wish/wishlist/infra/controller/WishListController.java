package com.berkson.wish.wishlist.infra.controller;

import com.berkson.wish.wishlist.aplication.service.RequestService;
import com.berkson.wish.wishlist.infra.controller.dto.AddProductRequest;
import com.berkson.wish.wishlist.infra.controller.dto.CheckProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.ProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.WishListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "WishList", description = "API para gerenciamento de listas de desejos de clientes")
public class WishListController {
    private final RequestService requestService;

    @Operation(
            summary = "Adicionar produto à lista de desejos",
            description = "Adiciona um novo produto à lista de desejos de um cliente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto adicionado com sucesso",
                    content = @Content(schema = @Schema(implementation = WishListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (ex: produto já existe na lista ou limite atingido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content
            )
    })
    @PostMapping("/{customerId}/products")
    public Mono<ResponseEntity<WishListResponse>> addProduct(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String customerId,
            @Parameter(description = "Dados do produto a ser adicionado", required = true) @RequestBody AddProductRequest request) {
        return requestService.addProductToWishList(customerId, request.productId())
                .map(wishListResponse ->
                        ResponseEntity.status(HttpStatus.CREATED).body(wishListResponse))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()))
                .onErrorResume(IllegalStateException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Operation(
            summary = "Remover produto da lista de desejos",
            description = "Remove um produto da lista de desejos de um cliente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto removido com sucesso",
                    content = @Content(schema = @Schema(implementation = WishListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto ou lista não encontrada",
                    content = @Content
            )
    })
    @DeleteMapping("/{customerId}/products/{productId}")
    public Mono<ResponseEntity<WishListResponse>> removeProduct(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String customerId,
            @Parameter(description = "ID do produto a ser removido", required = true) @PathVariable String productId) {
        return requestService.removeProductFromWishList(customerId, productId)
                .map(wishListResponse ->
                        ResponseEntity.status(HttpStatus.OK).body(wishListResponse))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()))
                .onErrorResume(IllegalStateException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @Operation(
            summary = "Listar produtos da lista de desejos",
            description = "Retorna todos os produtos da lista de desejos de um cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            )
    })
    @GetMapping("/{customerId}/products")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<ProductResponse>> getProducts(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String customerId) {
        return requestService.getWishListProducts(customerId);
    }

    @Operation(
            summary = "Verificar se produto existe na lista",
            description = "Verifica se um produto específico está presente na lista de desejos do cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Verificação realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CheckProductResponse.class))
            )
    })
    @GetMapping("/{customerId}/products/{productId}/exists")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CheckProductResponse> isProductInWishList(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String customerId,
            @Parameter(description = "ID do produto a ser verificado", required = true) @PathVariable String productId) {
        return requestService.isProductInWishList(customerId, productId);
    }
}
