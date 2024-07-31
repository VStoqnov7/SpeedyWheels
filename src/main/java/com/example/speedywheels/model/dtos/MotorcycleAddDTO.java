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
    @NotBlank(message = "{motorcycle.add.brandError}")
    private String brand;

    @NotBlank(message = "{motorcycle.add.modelError}")
    private String model;

    @Min(value = 1, message = "{motorcycle.add.powerError}")
    private int power;

    @Min(value = 0, message = "{motorcycle.add.mileageError}")
    private int mileage;

    @NotNull(message = "{motorcycle.add.productionDateError}")
    @PastOrPresent(message = "{motorcycle.add.productionDateErrorPastOrPresent}")
    private LocalDateTime productionDate;

    @NotNull(message = "{motorcycle.add.colorError}")
    private Color color;

    @NotBlank(message = "{motorcycle.add.locationError}")
    private String location;

    @NotNull(message = "{motorcycle.add.transmissionError}")
    private TransmissionType transmission;

    @NotNull(message = "{motorcycle.add.euroStandardError}")
    private EuroStandard euroStandard;

    @Min(value = 1, message = "{motorcycle.add.cubicCapacityError}")
    private int cubicCapacity;

    @NotNull(message = "{motorcycle.add.engineTypeError}")
    private EngineType engineType;

    @NotNull(message = "{motorcycle.add.priceErrorNotNull}")
    @Min(value = 1, message = "{motorcycle.add.priceError}")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "Motorcycle category is required!")
    private MotorcycleCategory category;

    private boolean hasLuggageCase;

    private boolean hasSidecar;

    private boolean hasStabilityControl;
}
