package com.berkson.wish.wishlist.infra.persistence.document;

import com.berkson.wish.wishlist.domain.entity.WishList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
@Document(collection = "wishlists")
public class WishListDocument {
    @Id
    private String id;
    @Indexed(unique = true)
    private String customerId;
    private List<ProductDocument> products = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WishList other)) {
            return false;
        }
        return Objects.equals(this.id, other.getId())
               && Objects.equals(this.customerId, other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}
