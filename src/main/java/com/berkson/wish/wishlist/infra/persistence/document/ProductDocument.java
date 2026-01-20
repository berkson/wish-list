package com.berkson.wish.wishlist.infra.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {
    private String productId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ProductDocument other)) {
            return false;
        }
        return Objects.equals(this.productId, other.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

}
