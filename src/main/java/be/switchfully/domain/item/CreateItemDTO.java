package be.switchfully.domain.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateItemDTO {
    @NotBlank(message = "Name is required")
    private final String name;
    private final String description;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private final Double price;
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private final int amount;


    public CreateItemDTO(String name, String description, Double price, int amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

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

}
