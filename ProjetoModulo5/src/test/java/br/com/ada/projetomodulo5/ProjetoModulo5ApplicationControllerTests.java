package br.com.ada.projetomodulo5;

import br.com.ada.projetomodulo5.dto.request.ProdutoRequest;
import br.com.ada.projetomodulo5.dto.response.ProdutoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjetoModulo5ApplicationControllerTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void post_produto_rest() {

        ProdutoRequest requestDto = new ProdutoRequest();
        requestDto.setNome("client test");
        requestDto.setQuantidade(1000);

        ResponseEntity<ProdutoResponse> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoResponse.class);

        Assertions.assertNotNull(responseDto.getBody());
        Assertions.assertEquals(201, responseDto.getStatusCode().value());
        Assertions.assertNotNull(responseDto.getBody().getUuid());
        Assertions.assertEquals(requestDto.getNome(), responseDto.getBody().getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responseDto.getBody().getQuantidade());

    }

    @Test
    void put_produto_rest() {

        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("client test");
        produtoRequest.setQuantidade(1000);

        ResponseEntity<ProdutoResponse> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(produtoRequest),
                        ProdutoResponse.class);

        ProdutoRequest produtoRequestUpdate = new ProdutoRequest();

        produtoRequestUpdate.setNome("client test");
        produtoRequestUpdate.setQuantidade(100);
        produtoRequestUpdate.setUuid(responseDto.getBody().getUuid());

        ResponseEntity<ProdutoResponse> responsePutDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.PUT,
                        new HttpEntity<>(produtoRequestUpdate),
                        ProdutoResponse.class);

        Assertions.assertNotNull(responsePutDto.getBody());
        Assertions.assertEquals(202, responsePutDto.getStatusCode().value());
        Assertions.assertNotNull(responsePutDto.getBody().getUuid());
        Assertions.assertEquals(produtoRequest.getNome(), responsePutDto.getBody().getNome());
        Assertions.assertEquals(100, responsePutDto.getBody().getQuantidade());

    }

    @Test
    void delete_cliente_by_uuid() {

        ProdutoRequest requestDto = new ProdutoRequest();
        requestDto.setNome("cliente test");
        requestDto.setQuantidade(100);

        ResponseEntity<ProdutoResponse> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoResponse.class);

        String uuid = responseDto.getBody().getUuid();

        ResponseEntity<ProdutoResponse> responseDeleteDto =
                restTemplate.exchange(
                        "/produto/" + uuid,
                        HttpMethod.DELETE,
                        null,
                        ProdutoResponse.class);

        ProdutoResponse responseBody = responseDeleteDto.getBody();

        Assertions.assertEquals(202, responseDeleteDto.getStatusCode().value());

        ResponseEntity<ProdutoResponse> responseGetDto =
                restTemplate.exchange(
                        "/produto/" + uuid,
                        HttpMethod.GET,
                        null,
                        ProdutoResponse.class);

        Assertions.assertEquals(404, responseGetDto.getStatusCode().value());

    }

    @Test
    void get_cliente_by_uuid() {

        ProdutoRequest requestDto = new ProdutoRequest();
        requestDto.setNome("cliente test");
        requestDto.setQuantidade(100);

        ResponseEntity<ProdutoResponse> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoResponse.class);

        String uuid = responseDto.getBody().getUuid();

        ResponseEntity<ProdutoResponse> responseGetDto =
                restTemplate.exchange(
                        "/produto/" + uuid,
                        HttpMethod.GET,
                        null,
                        ProdutoResponse.class);

        ProdutoResponse responseBody = responseGetDto.getBody();

        Assertions.assertEquals(202, responseGetDto.getStatusCode().value());
        Assertions.assertEquals(uuid, responseBody.getUuid());
        Assertions.assertEquals(requestDto.getNome(), responseBody.getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responseBody.getQuantidade());

    }

    @Test
    void listar_todos_clientes() {

        ProdutoRequest requestDto = new ProdutoRequest();
        requestDto.setNome("produto test listar 1");
        requestDto.setQuantidade(100);

        ResponseEntity<ProdutoResponse> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoResponse.class);

        String uuid = responseDto.getBody().getUuid();

        requestDto = new ProdutoRequest();
        requestDto.setNome("produto test listar 2");
        requestDto.setQuantidade(200);

        responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoResponse.class);

        String uuid2 = responseDto.getBody().getUuid();

        ResponseEntity<List<ProdutoResponse>> responseGetDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProdutoResponse>>() {});

        List<ProdutoResponse> clientesList = responseGetDto.getBody();

        Assertions.assertFalse(clientesList.isEmpty());

        boolean existsCliente1 = false;
        boolean existsCliente2 = false;
        for (ProdutoResponse clienteResponseDto : clientesList) {
            if (clienteResponseDto.getUuid().equals(uuid)) {
                existsCliente1 = true;
            } else if (clienteResponseDto.getUuid().equals(uuid2)) {
                existsCliente2 = true;
            }
        }

        Assertions.assertTrue(existsCliente1);

        Assertions.assertTrue(existsCliente2);

        Assertions.assertTrue(
                clientesList.stream().anyMatch(clienteResponseDto -> clienteResponseDto.getUuid().equals(uuid))
        );

        Assertions.assertTrue(
                clientesList.stream().anyMatch(clienteResponseDto -> clienteResponseDto.getUuid().equals(uuid2))
        );

    }


}
