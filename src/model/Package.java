package model;

import java.util.Map;

public class Package {
    private Long packageId;

    // Key denotes a Goods Id,
    // Value is quantity of particular goods
    private Map<Long, Integer> items;

    /**
     * @return the packageId
     */
    public Long getPackageId() {
        return packageId;
    }

    /**
     * @param packageId the packageId to set
     */
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    /**
     * @return the items
     */
    public Map<Long, Integer> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}