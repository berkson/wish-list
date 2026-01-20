package com.berkson.wish.wishlist.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

class WishListTest {

    @Test
    @DisplayName("Deve criar lista de desejos com ID do cliente")
    void shouldCreateWishlistWithCustomerId() {
        WishList wishList = WishList.create("customer-001");

        assertNotNull(wishList);
        assertEquals("customer-001", wishList.getCustomerId());
        assertThat(wishList.getProducts().isEmpty());
    }

    @Test
    @DisplayName("Não deve criar lista de desejos com ID do cliente nulo")
    void shouldNotCreateWishlistWithNullCustomerId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            WishList.create(null);
        });

        String expectedMessage = "O ID do cliente não pode ser nulo ou vazio";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Não deve criar lista de desejos com ID do cliente vazio")
    void shouldNotCreateWishlistWithEmptyCustomerId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            WishList.create("");
        });

        String expectedMessage = "O ID do cliente não pode ser nulo ou vazio";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("Deve adicionar produto à lista de desejos")
    void shouldAddProductToWishlist() {
        WishList wishList = WishList.create("customer-001");
        Product product = Product.create("product-001");

        wishList.addProduct(product);

        assertEquals(1, wishList.getProducts().size());
        assertThat(wishList.containsProduct("product-001"));
    }

    @Test
    @DisplayName("Não deve adicionar produto nulo")
    void shouldNotAddNullProduct() {
        WishList wishList = WishList.create("customer-001");
        assertThatThrownBy(() -> wishList.addProduct(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O produto não pode ser nulo");
    }

    @Test
    @DisplayName("Não deve adicionar produto duplicado")
    void shouldNotAddDuplicateProduct() {
        WishList wishList = WishList.create("customer-001");
        Product product = Product.create("product-001");
        wishList.addProduct(product);
        assertThatThrownBy(() -> wishList.addProduct(product))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O produto já existe na lista de desejos");
    }

    @Test
    @DisplayName("Não deve adicionar mais de 20 produtos")
    void shouldNotAddMoreThan20Products() {
        WishList wishList = WishList.create("customer-001");
        // Adiciona 20 produtos
        for (int i = 1; i <= 20; i++) {
            Product product = Product.create("product-" + String.format("%03d", i));
            wishList.addProduct(product);
        }

        assertEquals(20, wishList.getProducts().size());

        // Tenta colocar 21 produtos
        assertThatThrownBy(() -> wishList.addProduct(Product.create("product-021")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("A lista de desejos atingiu a capacidade máxima de 20 produtos");
    }

    @Test
    @DisplayName("Deve remover produto da lista de desejos")
    void shouldRemoveProductFromWishlist() {
        WishList wishList = WishList.create("customer-001");
        Product product = Product.create("product-001");
        wishList.addProduct(product);

        wishList.removeProduct("product-001");

        assertEquals(0, wishList.getProducts().size());
        assertFalse(wishList.containsProduct("product-001"));
    }

    @Test
    @DisplayName("Não deve remover produto que não existe")
    void shouldNotRemoveProductThatDoesntExist() {
        WishList wishList = WishList.create("customer-001");
        assertThatThrownBy(() -> wishList.removeProduct("product-999"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Produto não encontrado na lista de desejos");
    }

    @Test
    @DisplayName("Não deve remover produto com ID nulo")
    void shouldNotRemoveProductWithNullId() {
        WishList wishList = WishList.create("customer-001");
        assertThatThrownBy(() -> wishList.removeProduct(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID do produto não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Deve verificar se o produto existe na lista de desejos")
    void shouldCheckIfProductExistsInWishlist() {
        WishList wishList = WishList.create("customer-001");
        Product product = Product.create("product-001");
        wishList.addProduct(product);
        assertTrue(wishList.containsProduct("product-001"));
        assertThat(wishList.containsProduct("product-999")).isFalse();
    }

    @Test
    @DisplayName("Deve retornar falso para verificação de ID de produto nulo")
    void shouldReturnFalseForNullProductIdCheck() {
        WishList wishList = WishList.create("customer-001");
        assertThat(wishList.containsProduct(null)).isFalse();
    }

    @Test
    @DisplayName("Deve obter todos os produtos da lista de desejos")
    void shouldGetAllProductsFromWishlist() {
        WishList wishList = WishList.create("customer-001");
        wishList.addProduct(Product.create("product-001"));
        wishList.addProduct(Product.create("product-002"));
        wishList.addProduct(Product.create("product-003"));

        assertThat(wishList.getAllProducts())
                .hasSize(3)
                .extracting(Product::getProductId)
                .containsExactly("product-001", "product-002", "product-003");

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando a lista de desejos estiver vazia")
    void shouldReturnEmptyListWhenWishlistIsEmpty() {
        WishList wishList = WishList.create("customer-001");

        assertThat(wishList.getAllProducts()).isEmpty();
    }
}