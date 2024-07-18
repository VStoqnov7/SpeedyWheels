package com.example.speedywheels.model.dtos;

import com.example.speedywheels.model.enums.*;
import com.example.speedywheels.validation.vehiclePicturesValidator.ValidPhotos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class MotorcycleAddDTO {
    @NotBlank(message = "Brand is required!")
    private String brand;

    @NotBlank(message = "Model is required!")
    private String model;

    @Min(value = 1, message = "Power must be at least 1!")
    private int power;

    @Min(value = 0, message = "Mileage must be a non-negative number!")
    private int mileage;

    @NotNull(message = "Production date is required!")
    @PastOrPresent(message = "Production date must be in the past or present!")
    private LocalDateTime productionDate;

    @NotNull(message = "Color is required!")
    private Color color;

    @NotBlank(message = "Location is required!")
    private String location;

    @NotNull(message = "Transmission type is required!")
    private TransmissionType transmission;

    @NotNull(message = "Euro standard is required!")
    private EuroStandard euroStandard;

    @Min(value = 1, message = "Cubic capacity must be at least 1!")
    private int cubicCapacity;

    @NotNull(message = "Engine type is required!")
    private EngineType engineType;

    @NotNull(message = "Price date is required!")
    @Min(value = 1, message = "Price must be greater than zero!")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "Motorcycle category is required!")
    private MotorcycleCategory category;

    private boolean hasLuggageCase;

    private boolean hasSidecar;

    private boolean hasStabilityControl;
}
