package br.com.ada.projetomodulo5.controller;

import br.com.ada.projetomodulo5.dto.request.ProdutoRequest;
import br.com.ada.projetomodulo5.dto.response.ProdutoResponse;
import br.com.ada.projetomodulo5.entity.Produto;
import br.com.ada.projetomodulo5.factory.ProdutoFactory;
import br.com.ada.projetomodulo5.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse create (@RequestBody @Valid ProdutoRequest produtoRequest) {

        Produto produtoModel = produtoService.create(
                ProdutoFactory.dtoToProduto(produtoRequest));

        ProdutoResponse produtoResponse = new ProdutoResponse();

        produtoResponse.setNome(produtoModel.getNome());
        produtoResponse.setQuantidade(produtoModel.getQuantidade());
        produtoResponse.setUuid(produtoModel.getUuid());

        return produtoResponse;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProdutoResponse read(@PathVariable String uuid) {
        Produto produto = produtoService.getByUuid(uuid);
        return ProdutoFactory.produtoToDto(produto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProdutoResponse> list() {
        return ProdutoFactory.produtosToDtos(produtoService.list());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProdutoResponse update(@RequestBody @Valid ProdutoRequest produtoRequest) {
        Produto produto = ProdutoFactory.dtoToProduto(produtoRequest);
        return ProdutoFactory.produtoToDto(
                produtoService.update(produto));

    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String uuid) {
        produtoService.delete(uuid);
    }

}
