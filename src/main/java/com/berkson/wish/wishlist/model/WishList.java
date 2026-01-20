package com.berkson.wish.wishlist.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishList {
    private static final int MAX_PRODUCTS = 20;

    private String id;
    private String customerId;
    private List<Product> products = new java.util.ArrayList<>();

    public WishList(String customerId) {
        this.customerId = customerId;
        this.products = new java.util.ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo");
        }

        if (products.size() >= MAX_PRODUCTS) {
            throw new IllegalStateException("A lista de desejos atingiu a capacidade máxima de " + MAX_PRODUCTS + " produtos");
        }

        if (containsProduct(product.getProductId())) {
            throw new IllegalStateException("O produto já existe na lista de desejos");
        }

        products.add(product);
    }

    public boolean containsProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return false;
        }
        return products.stream().anyMatch(p -> p.getProductId().equals(productId));
    }

    public void removeProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do produto não pode ser nulo ou vazio");
        }

        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));

        if (!removed) {
            throw new IllegalStateException("Produto não encontrado na lista de desejos");
        }
    }

    public List<Product> getAllProducts() {
        return new java.util.ArrayList<>(products);
    }

    public static WishList create(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do cliente não pode ser nulo ou vazio");
        }
        return new WishList(customerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WishList other)) {
            return false;
        }
        return Objects.equals(this.id, other.id)
               && Objects.equals(this.customerId, other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}
