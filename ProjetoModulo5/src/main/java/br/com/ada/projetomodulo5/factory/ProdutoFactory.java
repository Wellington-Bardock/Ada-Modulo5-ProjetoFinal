package br.com.ada.projetomodulo5.factory;

import br.com.ada.projetomodulo5.dto.request.ProdutoRequest;
import br.com.ada.projetomodulo5.dto.response.ProdutoResponse;
import br.com.ada.projetomodulo5.entity.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoFactory {

    private ProdutoFactory(){}

    public static ProdutoResponse produtoToDto(Produto produto) {

        if (produto == null) {
            return null;
        }

        ProdutoResponse produtoResponse = new ProdutoResponse();

        produtoResponse.setNome(produto.getNome());
        produtoResponse.setQuantidade(produto.getQuantidade());
        produtoResponse.setUuid(produto.getUuid());

        return produtoResponse;

    }

    public static Produto dtoToProduto(ProdutoRequest produtoRequest) {

        if (produtoRequest == null) {
            return null;
        }

        Produto produto = new Produto();

        produto.setNome(produtoRequest.getNome());
        produto.setQuantidade(produtoRequest.getQuantidade());
        produto.setUuid(produtoRequest.getUuid());

        return produto;

    }

    public static List<ProdutoResponse> produtosToDtos(List<Produto> produtos) {
        List<ProdutoResponse> produtoDtos = new ArrayList<>();

        for (Produto produto: produtos) {
            produtoDtos.add(produtoToDto(produto));
        }

        return produtoDtos;

    }

}
