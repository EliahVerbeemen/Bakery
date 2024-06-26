package kdg.be.DTO;

public class IngredientDTO {
    private String departmentOfStorage;
    private long shelfLife;
    private Long ingredientId;

    public IngredientDTO(String departmentOfStorage, long shelfLife) {
        this.departmentOfStorage = departmentOfStorage;
        this.shelfLife = shelfLife;
    }

    public String getDepartmentOfStorage() {
        return departmentOfStorage;
    }

    public void setDepartmentOfStorage(String departmentOfStorage) {
        this.departmentOfStorage = departmentOfStorage;
    }

    public long getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(long shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
