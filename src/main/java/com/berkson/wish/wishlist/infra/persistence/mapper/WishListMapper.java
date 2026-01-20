package com.berkson.wish.wishlist.infra.persistence.mapper;

import com.berkson.wish.wishlist.infra.persistence.document.ProductDocument;
import com.berkson.wish.wishlist.infra.persistence.document.WishListDocument;
import com.berkson.wish.wishlist.model.Product;
import com.berkson.wish.wishlist.model.WishList;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 **/

@Component
public class WishListMapper {
    public WishListDocument toDocument(WishList wishList) {
        WishListDocument document = new WishListDocument();
        document.setId(wishList.getId());
        document.setCustomerId(wishList.getCustomerId());
        document.setProducts(wishList.getProducts()
                .stream().map(this::toProductDocument).toList());
        return document;
    }

    public WishList toDomain(WishListDocument document) {
        WishList wishList = new WishList();
        wishList.setId(document.getId());
        wishList.setCustomerId(document.getCustomerId());
        wishList.setProducts(document.getProducts()
                .stream().map(this::toProduct).toList());
        return wishList;
    }

    private Product toProduct(ProductDocument productDocument) {
        return new Product(productDocument.getProductId());
    }

    private ProductDocument toProductDocument(Product product) {
        return new ProductDocument(product.getProductId());
    }
}
