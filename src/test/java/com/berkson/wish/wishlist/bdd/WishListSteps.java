package com.berkson.wish.wishlist.bdd;

import com.berkson.wish.wishlist.infra.controller.dto.AddProductRequest;
import com.berkson.wish.wishlist.infra.controller.dto.CheckProductResponse;
import com.berkson.wish.wishlist.infra.controller.dto.ProductResponse;
import com.berkson.wish.wishlist.infra.persistence.repository.MongoWishListRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created By : Berkson Ximenes
 * Date : 21/01/2026
 **/

public class WishListSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoWishListRepository repository;
    private WebTestClient webTestClient;
    private WebTestClient.ResponseSpec lastResponse;
    private List<ProductResponse> lastProductList;
    private CheckProductResponse lastCheckProductResponse;
    private final Duration responseTimeout = Duration.ofSeconds(10);

    @Autowired
    public void setWebTestClient() {
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(responseTimeout)
                .build();
    }

    @Given("um banco de dados de wishlist vazio")
    public void aCleanWishlistDatabase() {
        repository.deleteAll().block();
    }

    @Given("o cliente {string} não tem wishlist")
    public void customerHasNoWishlist(String customerId) {
        repository.deleteByCustomerId(customerId).block();
    }

    @Given("o cliente {string} tem uma wishlist com {int} produtos")
    public void customerHasAWishlistWithProducts(String customerId, int productCount) {
        customerHasNoWishlist(customerId);
        for (int i = 1; i <= productCount; i++) {
            String productId = String.format("product-%03d", i);
            webTestClient.post()
                    .uri("/api/v1/wishlists/" + customerId + "/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new AddProductRequest(productId))
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CREATED);
        }
    }

    @When("o cliente {string} adiciona o produto {string} à wishlist")
    @Given("o cliente {string} adiciona o produto {string} à wishlist")
    public void customerAddsProductToWishlist(String customerId, String productId) {
        lastResponse = webTestClient.post()
                .uri("/api/v1/wishlists/" + customerId + "/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AddProductRequest(productId))
                .exchange();
    }

    @When("o cliente {string} tenta adicionar o produto {string} à wishlist")
    public void customerTriesToAddProductToWishlist(String customerId, String productId) {
        customerAddsProductToWishlist(customerId, productId);
    }

    @When("o cliente {string} remove o produto {string} da wishlist")
    public void customerRemovesProductFromWishlist(String customerId, String productId) {
        lastResponse = webTestClient.delete()
                .uri("/api/v1/wishlists/" + customerId + "/products/" + productId)
                .exchange();
    }

    @When("o cliente {string} tenta remover o produto {string} da wishlist")
    public void customerTriesToRemoveProductFromWishlist(String customerId, String productId) {
        customerRemovesProductFromWishlist(customerId, productId);
    }

    @When("o cliente {string} requisita todos os produtos da wishlist")
    public void customerRequestsAllProductsInWishlist(String customerId) {
        lastResponse = webTestClient.get()
                .uri("/api/v1/wishlists/" + customerId + "/products")
                .exchange();

        lastProductList = lastResponse
                .expectStatus().isOk()
                .expectBodyList(ProductResponse.class)
                .returnResult()
                .getResponseBody();
    }

    @When("o cliente {string} verifica se o produto {string} existe na wishlist")
    public void customerChecksIfProductIsInWishlist(String customerId, String productId) {
        lastResponse = webTestClient.get()
                .uri("/api/v1/wishlists/" + customerId + "/products/" + productId)
                .exchange();

        lastCheckProductResponse = lastResponse
                .expectStatus().isOk()
                .expectBody(CheckProductResponse.class)
                .returnResult()
                .getResponseBody();
    }

    @Then("a wishlist do cliente {string} deve conter o produto {string}")
    public void wishlistShouldContainProduct(String customerId, String productId) {
        webTestClient.get()
                .uri("/api/v1/wishlists/" + customerId + "/products/" + productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CheckProductResponse.class)
                .value(response -> {
                    assertThat(response.exists()).isTrue();
                });
    }

    @Then("a wishlist do cliente {string} não deve conter o produto {string}")
    public void wishlistShouldNotContainProduct(String customerId, String productId) {
        webTestClient.get()
                .uri("/api/v1/wishlists/" + customerId + "/products/" + productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CheckProductResponse.class)
                .value(response -> {
                    assertThat(response.exists()).isFalse();
                });
    }

    @Then("a wishlist do cliente {string} deve ter {int} produto(s)")
    public void wishlistShouldHaveProducts(String customerId, int expectedCount) {
        webTestClient.get()
                .uri("/api/v1/wishlists/" + customerId + "/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponse.class)
                .hasSize(expectedCount);
    }

    @Then("a operação deve falhar com status {int}")
    public void operationShouldFailWithStatus(int expectedStatus) {
        lastResponse.expectStatus().isEqualTo(HttpStatus.valueOf(expectedStatus));
    }

    @Then("a resposta deve conter {int} produto(s)")
    public void responseShouldContainProducts(int expectedCount) {
        assertThat(lastProductList).hasSize(expectedCount);
    }

    @Then("a resposta deve incluir o produto {string}")
    public void responseShouldIncludeProduct(String productId) {
        assertThat(lastProductList)
                .extracting(ProductResponse::productId)
                .contains(productId);
    }

    @Then("a verificação deve retornar {word}")
    public void checkShouldReturn(String expectedExistence) {
        boolean expected = Boolean.parseBoolean(expectedExistence);
        assertThat(lastCheckProductResponse.exists()).isEqualTo(expected);
    }
}
