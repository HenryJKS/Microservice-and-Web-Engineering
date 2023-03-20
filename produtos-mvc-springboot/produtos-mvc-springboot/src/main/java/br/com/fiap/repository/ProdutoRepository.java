package br.com.fiap.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.fiap.model.ProdutoModel;

public class ProdutoRepository {
	private static Map<Long, ProdutoModel> produtos;
	private static ProdutoRepository instance;
	
	private ProdutoRepository() {
		produtos = new HashMap<Long, ProdutoModel>();

		produtos.put(1L,
				new ProdutoModel(1L, "Nome produto 1", "SKU1", "Descricao produto 1", 100.00, "Detalhe produto 1"));
		produtos.put(2L,
				new ProdutoModel(2L, "Nome produto 2", "SKU2", "Descricao produto 2", 200.00, "Detalhe produto 2"));
		produtos.put(3L,
				new ProdutoModel(3L, "Nome produto 3", "SKU3", "Descricao produto 3", 300.00, "Detalhe produto 3"));
	}

	//SINGLETON
	public static ProdutoRepository getInstance() {
		if(instance == null) {
			instance = new ProdutoRepository();
		}
		return instance;
	}
	
	
	public ArrayList<ProdutoModel> findAll() {
		return new ArrayList<>(produtos.values());
	}

	public ProdutoModel findById(long id) {
		return produtos.get(id);
	}

	public void update(ProdutoModel produtoModel) {
		produtos.put(produtoModel.getId(), produtoModel);
	}

	public void save(ProdutoModel produto) {
		Long newID = (long) (produtos.size() + 1);
		produto.setId(newID);
		produtos.put(newID, produto);
	}
	
	public void deleteById(long id) {
		produtos.remove(id);
	}
	
}
