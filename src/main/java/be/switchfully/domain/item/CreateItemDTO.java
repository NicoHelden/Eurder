package be.switchfully.domain.item;

public class CreateItemDTO {
    private String name;
    private String description;
    private Double price;
    private int amount;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
