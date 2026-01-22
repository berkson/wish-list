Feature: Gerenciamento da Wishlist
  Como um cliente
  Eu quero gerenciar a minha wishlist
  Para que eu possa ficar ligado nos produtos do meu interesse

  Background:
    Given um banco de dados de wishlist vazio

  Scenario: Acrescentar um produto a uma lista vazia
    Given o cliente "cliente-001" não tem wishlist
    When o cliente "cliente-001" adiciona o produto "produto-001" à wishlist
    Then a wishlist do cliente "cliente-001" deve conter o produto "produto-001"
    And a wishlist do cliente "cliente-001" deve ter 1 produto

  Scenario: Adicionar vários produtos a wishlist
    Given o cliente "cliente-002" não tem wishlist
    When o cliente "cliente-002" adiciona o produto "produto-001" à wishlist
    And o cliente "cliente-002" adiciona o produto "produto-002" à wishlist
    And o cliente "cliente-002" adiciona o produto "produto-003" à wishlist
    Then a wishlist do cliente "cliente-002" deve conter o produto "produto-001"
    And a wishlist do cliente "cliente-002" deve conter o produto "produto-002"
    And a wishlist do cliente "cliente-002" deve conter o produto "produto-003"
    And a wishlist do cliente "cliente-002" deve ter 3 produtos

  Scenario: Não pode adicionar produto duplicado a wishlist
    Given o cliente "cliente-003" não tem wishlist
    And o cliente "cliente-003" adiciona o produto "produto-001" à wishlist
    When o cliente "cliente-003" tenta adicionar o produto "produto-001" à wishlist
    Then a operação deve falhar com status 422
    And a wishlist do cliente "cliente-003" deve ter 1 produto

  Scenario: Não pode acrescentar mais de 20 produtos a wishlist
    Given o cliente "cliente-004" tem uma wishlist com 20 produtos
    When o cliente "cliente-004" tenta adicionar o produto "produto-021" à wishlist
    Then a operação deve falhar com status 422

  Scenario: Remover um produto da wishlist
    Given o cliente "cliente-005" não tem wishlist
    And o cliente "cliente-005" adiciona o produto "produto-001" à wishlist
    And o cliente "cliente-005" adiciona o produto "produto-002" à wishlist
    When o cliente "cliente-005" remove o produto "produto-001" da wishlist
    Then a wishlist do cliente "cliente-005" não deve conter o produto "produto-001"
    And a wishlist do cliente "cliente-005" deve conter o produto "produto-002"
    And a wishlist do cliente "cliente-005" deve ter 1 produto

  Scenario: Não pode remover produto que não existe na wishlist
    Given o cliente "cliente-006" não tem wishlist
    And o cliente "cliente-006" adiciona o produto "produto-001" à wishlist
    When o cliente "cliente-006" tenta remover o produto "produto-999" da wishlist
    Then a operação deve falhar com status 404

  Scenario: Recuperar todos os produtos da wishlist
    Given o cliente "cliente-007" não tem wishlist
    And o cliente "cliente-007" adiciona o produto "produto-001" à wishlist
    And o cliente "cliente-007" adiciona o produto "produto-002" à wishlist
    And o cliente "cliente-007" adiciona o produto "produto-003" à wishlist
    When o cliente "cliente-007" requisita todos os produtos da wishlist
    Then a resposta deve conter 3 produtos
    And a resposta deve incluir o produto "produto-001"
    And a resposta deve incluir o produto "produto-002"
    And a resposta deve incluir o produto "produto-003"

  Scenario: Buscar todos os produtos de uma wishlist vazia
    Given o cliente "cliente-008" não tem wishlist
    When o cliente "cliente-008" requisita todos os produtos da wishlist
    Then a resposta deve conter 0 produtos

  Scenario: Checar se o produto existe na wishlist
    Given o cliente "cliente-009" não tem wishlist
    And o cliente "cliente-009" adiciona o produto "produto-001" à wishlist
    When o cliente "cliente-009" verifica se o produto "produto-001" existe na wishlist
    Then a verificação deve retornar verdadeiro

  Scenario: Checar se o produto não existe na wishlist
    Given o cliente "cliente-010" não tem wishlist
    And o cliente "cliente-010" adiciona o produto "produto-001" à wishlist
    When o cliente "cliente-010" verifica se o produto "produto-999" existe na wishlist
    Then a verificação deve retornar falso
