package br.com.ada.projetomodulo5.service;

import br.com.ada.projetomodulo5.entity.Produto;
import br.com.ada.projetomodulo5.exception.InvalidFieldException;
import br.com.ada.projetomodulo5.exception.NotFoundException;
import br.com.ada.projetomodulo5.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    public static final String PRODUCT_NOT_FOUND = "Produto não encontrado!";
    final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto create(Produto produto) {

        checkFields(produto);
        produto.setUuid(UUID.randomUUID().toString());
        return produtoRepository.saveAndFlush(produto);
    }

    public Produto update(Produto produto) {
        checkFields(produto);
        Produto produtoDB = getByUuid(produto.getUuid());
        produtoDB.setNome(produto.getNome());
        produtoDB.setQuantidade(produto.getQuantidade());
        produtoRepository.saveAndFlush(produtoDB);
        return produtoDB;
    }

    public void delete(String uuid) {

        Produto productDB = getByUuid(uuid);
        produtoRepository.delete(productDB);
    }

    public List<Produto> list() {
        return produtoRepository.findAll();
    }

    public Produto getByUuid(String uuid) {

        List<Produto> produtos = produtoRepository.findByUuid(uuid);

        if (produtos.size() == 1) {
            return produtos.get(0);
        } else {
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    public void checkFields(Produto produto) {

        if (produto.getNome() == null || produto.getQuantidade() == null) {
            throw new InvalidFieldException("Possui campo vazio!");

        } else if (produto.getQuantidade().compareTo(0) < 0) {
            throw new InvalidFieldException("Não é permitido valor menor que zero!");
        }
    }
}
