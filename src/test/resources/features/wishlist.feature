Feature: Gerenciamento da Wishlist
  Como um cliente
  Eu quero gerenciar a minha wishlist
  Para que eu possa ficar ligado nos produtos do meu interesse

  Background:
    Given um banco de dados de wishlist vazio

  Scenario: Acrescentar um produto a uma lista vazia
    Given cliente "customer-001" não tem wishlist
    When cliente "customer-001" adicona o produto "product-001" a wishlist
    Then a wishlist para o cliente "customer-001" deve conter o produto "product-001"
    And a wishlist para o cliente "customer-001" deve ter 1 produto

  Scenario: adicionar vários produtos a wishlist
    Given o cliente "customer-002" não tem wishlist
    When o cliente "customer-002" adiciona o produto "product-001" a wishlist
    And o cliente "customer-002" adiciona o produto "product-002" a wishlist
    And o cliente "customer-002" adiciona o produto "product-003" a wishlist
    Then a wishlist para o cliente "customer-002" deve conter o produto "product-001"
    And a wishlist para o cliente "customer-002" deve conter o produto "product-002"
    And a wishlist para o cliente "customer-002" deve conter o produto "product-003"
    And a wishlist para o cliente "customer-002" deve ter 3 produtos

  Scenario: Não pode adicionar produto duplicado a wishlist
    Given o cliente "customer-003" não tem wishlist
    And o cliente "customer-003" adiciona o produto "product-001" a wishlist
    When o cliente "customer-003" tenta adicionar o produto "product-001" a wishlist
    Then a operação deve falhar com status 422
    And a wishlist para o cliente "customer-003" deve ter 1 produto

  Scenario: Não pode acrescentar mais de 20 produtos a wishlist
    Given o cliente "customer-004" tem uma wishlist com 20 produtos
    When o cliente "customer-004" tenta adicionar o produto "product-021" a wishlist
    Then a operação deve falhar com status 422

  Scenario: Remover um produto da wishlist
    Given o cliente "customer-005" não tem wishlist
    And o cliente "customer-005" adiciona o produto "product-001" a wishlist
    And o cliente "customer-005" adiciona o produto "product-002" a wishlist
    When o cliente "customer-005" remove o produto "product-001" da wishlist
    Then a wishlist para o cliente "customer-005" não deve conter o produto "product-001"
    And a wishlist para o cliente "customer-005" deve conter o produto "product-002"
    And a wishlist para o cliente "customer-005" deve ter 1 produto

  Scenario: Não pode remover produto que não existe na wishlist
    Given o cliente "customer-006" não tem wishlist
    And o cliente "customer-006" adiciona o produto "product-001" a wishlist
    When o cliente "customer-006" tenta remover o produto "product-999" da wishlist
    Then a operação deve falhar com status 404

  Scenario: Recuperar todos os produtos da wishlist
    Given o cliente "customer-007" não tem wishlist
    And o cliente "customer-007" adiciona o produto "product-001" a wishlist
    And o cliente "customer-007" adiciona o produto "product-002" a wishlist
    And o cliente "customer-007" adiciona o produto "product-003" a wishlist
    When o cliente "customer-007" requisita todos os produtos da wishlist
    Then a resposta deve conter 3 produtos
    And a resposta deve incluir o produto "product-001"
    And a resposta deve incluir o produto "product-002"
    And a resposta deve incluir o produto "product-003"

  Scenario: Buscar todos os produtos de uma wishlist vazia
    Given o cliente "customer-008" não tem wishlist
    When o cliente "customer-008" requisita todos os produtos da wishlist
    Then a resposta deve conter 0 produtos

  Scenario: Checar se o produto existe na wishlist
    Given o cliente "customer-009" não tem wishlist
    And o cliente "customer-009" adiciona o produto "product-001" a wishlist
    When o cliente "customer-009" verifica se o produto "product-001" existe na wishlist
    Then a verificação deve retornar verdadero

  Scenario: Check if product does not exist in wishlist
    Given o cliente "customer-010" não tem wishlist
    And o cliente "customer-010" adiciona o produto "product-001" a wishlist
    When o cliente "customer-010" verifica se o produto "product-999" existe na wishlist
    Then a verificação deve retornar falso
