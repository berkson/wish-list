package com.berkson.wish.wishlist.model;

import com.berkson.wish.wishlist.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

class ProductTest {

    @Test
    @DisplayName("Deve criar produto com ID")
    void shouldCreateProductWithId() {
        Product product = Product.create("product-001");

        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo("product-001");
    }

    @Test
    @DisplayName("Não deve criar produto com ID nulo")
    void shouldNotCreateProductWithNullId() {
        assertThatThrownBy(() -> Product.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID do produto não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Não deve criar produto com ID vazio")
    void shouldNotCreateProductWithEmptyId() {
        assertThatThrownBy(() -> Product.create(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID do produto não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Não deve criar produto com ID em branco")
    void shouldNotCreateProductWithBlankId() {
        assertThatThrownBy(() -> Product.create("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID do produto não pode ser nulo ou vazio");
    }
}