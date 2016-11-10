package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import model.Goods;
import model.Package;

@Service
public class PackageServiceInMemory {
	private Map<Long, Package> storage;
	private static Long seq = 1L;
	private GoodsServiceInMemory goodsService;

	public PackageServiceInMemory(GoodsServiceInMemory goodsService) {
	    /*
	    * Ideally, instead of injecting the service,
	    * it should be a service client and we should define wrapper methods calling service APIs and,
	    * fulfilling our needs for the current service.
	    * Or, we can communicate with DB layer to get relevant data
	    *
	    * In this case, our best choice should be DB layer,
	    * because using goodsService would require synchronous communication which denotes strong coupling, BAD!
	    */
		this.goodsService = goodsService;
		storage = new ConcurrentHashMap<>();
	}

	public Package getPackageById(Long id) throws NullPointerException {
        if(id == null) {
            throw new NullPointerException("Input parameter for getting package cannot be null!");
        }

        return storage.get(id);
    }

	public List<Package> getAllPackages() {
        return new ArrayList<>(storage.values());
    }

	public Long createPackage(Package p) throws NullPointerException, DataIntegrityViolationException {
		if (p == null) {
			throw new NullPointerException("Input parameter for creating a new package cannot be null!");
		}

		if(!isValidGoods(p.getItems())) {
            throw new DataIntegrityViolationException("Inappropriate addition of goods to a package where some of the goods doesn't even exist in DB!");
        }

		p.setPackageId(++seq);
		storage.put(p.getPackageId(), p);
		return p.getPackageId();
	}

	public void addGoodsToPackage(Long packageId, Map<Long, Integer> goods) throws NullPointerException, IllegalArgumentException, DataIntegrityViolationException {
		if(packageId == null || goods == null) {
			throw new NullPointerException("Input parameters for adding goods to a package cannot be null!");
		}

		if(!storage.containsKey(packageId)) {
			throw new IllegalArgumentException("There doesn't exist any package id " + packageId + " in our records!");
		}

		if(!isValidGoods(goods)) {
			throw new DataIntegrityViolationException("Inappropriate addition of goods to a package where some of the goods doesn't even exist in DB!");
		}

		storage.get(packageId).getItems().putAll(goods);
	}

	private boolean isValidGoods(Map<Long, Integer> goods) {
		List<Long> goodsIds = new ArrayList<>(goods.keySet());
		List<Goods> goodsFromService = goodsService.get(goodsIds);
		return goodsIds.size() == goodsFromService.size();
	}

	public List<Long> getAllLimitedQuantityPackages() {
		List<Long> packages = new ArrayList<>();

		for(Long packageId : storage.keySet()) {
			Package p = storage.get(packageId);
			Map<Long, Integer> items = p.getItems();
			boolean isLimitedQuantityPackage = true;

			List<Long> goodsIds = new ArrayList<>(items.keySet());
			List<Goods> goodsFromService = goodsService.get(goodsIds);

			for(Goods goods : goodsFromService) {
				// Assuming, all non hazmat goods can be shipped as limited quantity
				// Verifying only for hazmat goods if package has more quantity than specified limited quantity
				if(goods.isHazmat() && items.get(goods.getId()).intValue() > goods.getMaxCountForLimtedQuantity()) {
					isLimitedQuantityPackage = false;
					break;
				}
			}

			if(isLimitedQuantityPackage) {
				packages.add(packageId);
			}
		}

		return packages;
	}
}