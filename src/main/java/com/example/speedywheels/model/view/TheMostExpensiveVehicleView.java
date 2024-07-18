package com.example.speedywheels.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TheMostExpensiveVehicleView {

    private List<String> photosUrls;
}
