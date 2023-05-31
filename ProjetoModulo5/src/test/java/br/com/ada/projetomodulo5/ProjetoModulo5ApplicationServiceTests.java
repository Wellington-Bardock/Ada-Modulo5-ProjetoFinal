package br.com.ada.projetomodulo5;

import br.com.ada.projetomodulo5.entity.Produto;
import br.com.ada.projetomodulo5.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjetoModulo5ApplicationServiceTests {

    public static final String POSSUI_CAMPO_VAZIO = "Possui campo vazio!";
    @Autowired
    ProdutoService produtoService;

    @Test
    void create_product_ok() {

        Produto produto = new Produto();

        produto.setNome("Produto 1");
        produto.setQuantidade(1000);

        Produto produtoCreated = produtoService.create(produto);

        Assertions.assertEquals(produto.getNome(), produtoCreated.getNome());
        Assertions.assertEquals(produto.getQuantidade(), produtoCreated.getQuantidade());
        Assertions.assertNotNull(produto.getUuid());
        Assertions.assertNotNull(produto.getId());

    }

    @Test
    void create_with_invalid_qtd() {

        Produto produto = new Produto();

        produto.setNome("Produto 1");
        produto.setQuantidade(-20);

        Exception exception = Assertions.assertThrows(Exception.class, () ->

            produtoService.create(produto));

        Assertions.assertEquals("Não é permitido valor menor que zero!",exception.getMessage());

    }

    @Test
    void create_product_with_a_field_null() {

        Produto produto = new Produto();
        produto.setQuantidade(1000);

        Exception exception = Assertions.assertThrows(Exception.class, () ->
                produtoService.create(produto));

        Assertions.assertEquals(POSSUI_CAMPO_VAZIO, exception.getMessage());

    }

    @Test
    void read_by_uuid_ok() {

        Produto produto = new Produto();

        produto.setNome("Produto 9");
        produto.setQuantidade(1000);

        Produto productCreated = produtoService.create(produto);

        Produto productFound = produtoService.getByUuid(productCreated.getUuid());

        Assertions.assertEquals(produto.getNome(), productFound.getNome());
        Assertions.assertEquals(produto.getQuantidade(), productFound.getQuantidade());
        Assertions.assertEquals(productCreated.getId(), productFound.getId());
        Assertions.assertEquals(productCreated.getUuid(), productFound.getUuid());

    }

    @Test
    void read_by_uuid_nok() {

        Produto produto = new Produto();

        produto.setNome("Produto 10");
        produto.setQuantidade(1000);
        String uuidInvalido = "uuidInvalido";

        produtoService.create(produto);

        Exception exception = Assertions.assertThrows(Exception.class, () ->

                produtoService.getByUuid(uuidInvalido));

        Assertions.assertEquals("Produto não encontrado!", exception.getMessage());
    }

    @Test
    void list_products_ok() {

        Produto produto = new Produto();

        produto.setNome("Produto 8");
        produto.setQuantidade(1000);

        List<Produto> produtoList = produtoService.list();

        Assertions.assertNotNull(produtoList);
    }

    @Test
    void update_product_ok() {

        Produto produto = new Produto();

        produto.setNome("Produto 4");
        produto.setQuantidade(1000);

        Produto produtoCreated = produtoService.create(produto);

        Produto produtoToUpdate = new Produto();

        produtoToUpdate.setNome("Produto 4");
        produtoToUpdate.setQuantidade(100);
        produtoToUpdate.setUuid(produto.getUuid());

        Produto produtoUpdated = produtoService.update(produtoToUpdate);

        Assertions.assertEquals(produto.getNome(), produtoUpdated.getNome());
        Assertions.assertNotEquals(produtoUpdated.getQuantidade(), produto.getQuantidade());
        Assertions.assertEquals(produtoCreated.getId(),produtoUpdated.getId());
        Assertions.assertNotNull(produto.getId());
        Assertions.assertNotNull(produto.getUuid());
        Assertions.assertEquals(produtoCreated.getUuid(), produtoUpdated.getUuid());

    }

    @Test
    void delete_product_ok() {

        Produto produto = new Produto();

        produto.setNome("Produto 5");
        produto.setQuantidade(1000);

        Produto productCreated = produtoService.create(produto);

        produtoService.delete(productCreated.getUuid());

        Exception exception = Assertions.assertThrows(Exception.class, () ->

                produtoService.getByUuid(productCreated.getUuid()));

        Assertions.assertEquals("Produto não encontrado!", exception.getMessage());
    }

    @Test
    void check_product_with_invalid_qtd() {

        Produto produto = new Produto();

        produto.setNome("Produto 3");
        produto.setQuantidade(1000);
        produtoService.create(produto);

        produto.setNome("Produto 3");
        produto.setQuantidade(-10);

        Exception exception = Assertions.assertThrows(Exception.class, () ->

                produtoService.create(produto));

        Assertions.assertEquals("Não é permitido valor menor que zero!", exception.getMessage());

    }

}
