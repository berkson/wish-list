package com.berkson.wish.wishlist.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private String productId;

    public static Product create(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do produto não pode ser nulo ou vazio");
        }
        return new Product(productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product other)) {
            return false;
        }
        return Objects.equals(this.productId, other.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
