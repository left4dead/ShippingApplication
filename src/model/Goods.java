package model;

public class Goods {
    private Long id;
    private String description;
    private boolean isHazmat;
    private int maxCountForLimtedQuantity;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the isHazmat
     */
    public boolean isHazmat() {
        return isHazmat;
    }
    /**
     * @param isHazmat the isHazmat to set
     */
    public void setHazmat(boolean isHazmat) {
        this.isHazmat = isHazmat;
    }
    /**
     * @return the maxCountForLimtedQuantity
     */
    public int getMaxCountForLimtedQuantity() {
        return maxCountForLimtedQuantity;
    }
    /**
     * @param maxCountForLimtedQuantity the maxCountForLimtedQuantity to set
     */
    public void setMaxCountForLimtedQuantity(int maxCountForLimtedQuantity) {
        this.maxCountForLimtedQuantity = maxCountForLimtedQuantity;
    }
}