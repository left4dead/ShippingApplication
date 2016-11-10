package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import model.Goods;

@Service
public class GoodsServiceInMemory {
	private Map<Long, Goods> storage;
	private static Long seq = 1L;

	public GoodsServiceInMemory() {
		storage = new ConcurrentHashMap<>();
	}

	public Goods getGoodsById(Long id) throws NullPointerException {
		if(id == null) {
			throw new NullPointerException("Input parameter for getting goods cannot be null!");
		}

		return storage.get(id);
	}

	public List<Goods> get(List<Long> ids) throws NullPointerException {
		if(ids == null) {
			throw new NullPointerException("Input parameter for getting goods cannot be null!");
		}

		List<Goods> goods = new ArrayList<>();
		for(Long id : ids) {
			if(storage.containsKey(id)) {
				goods.add(storage.get(id));
			}
		}

		return goods;
	}

	public List<Goods> getAllGoods() {
		return new ArrayList<>(storage.values());
	}

	public Long createGoods(Goods goods) throws NullPointerException {
		if (goods == null) {
			throw new NullPointerException("Input parameter for creating goods cannot be null!");
		}

		goods.setId(++seq);
		storage.put(goods.getId(), goods);
		return goods.getId();
	}
}