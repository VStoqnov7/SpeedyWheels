package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;

import com.example.speedywheels.model.entity.UserFavoriteMotorcycle;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.enums.*;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.ModelAttributeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MotorcycleServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private MotorcycleRepository motorcycleRepository;
    @InjectMocks
    private MotorcycleServiceImpl motorcycleService;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    private MotorcycleAddDTO motorcycleAddDTO;
    private Motorcycle motorcycle;
    private User user;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        user = new User()
                .setUsername("testUser")
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("password")
                .setModified(LocalDateTime.now());

        MultipartFile photo1 = mock(MultipartFile.class);
        MultipartFile photo2 = mock(MultipartFile.class);

        motorcycleAddDTO = new MotorcycleAddDTO()
                .setModel("Ninja")
                .setProductionDate(LocalDateTime.now())
                .setPhotosUrls(List.of(photo1, photo2));

        motorcycle = new Motorcycle();
        motorcycle.setModel("Ninja");
        motorcycle.setPrice(BigDecimal.valueOf(15000.00));
        motorcycle.setProductionDate(LocalDateTime.now());
        motorcycle.setPhotosUrls(List.of("uploaded_photo1_url", "uploaded_photo2_url"));
        motorcycle.setRegisteredOn(LocalDateTime.now());
        motorcycle.setOwner(user);
        motorcycle.setBrand("Kawasaki");
        motorcycle.setPower(200);
        motorcycle.setMileage(1000);
        motorcycle.setColor(Color.GREEN);
        motorcycle.setLocation("Sofia");
        motorcycle.setTransmission(TransmissionType.MANUAL);
        motorcycle.setEuroStandard(EuroStandard.EURO_5);
        motorcycle.setCubicCapacity(650);
        motorcycle.setEngineType(EngineType.PETROL);
        motorcycle.setCategory(MotorcycleCategory.SPORT);
        motorcycle.setHasLuggageCase(true);
        motorcycle.setHasSidecar(false);
        motorcycle.setHasStabilityControl(true);

        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUser");
        lenient().when(cloudinaryService.saveImage(photo1)).thenReturn("uploaded_photo1_url");
        lenient().when(cloudinaryService.saveImage(photo2)).thenReturn("uploaded_photo2_url");
    }

    @Test
    void saveMotorcycle() {
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(motorcycleAddDTO, Motorcycle.class)).thenReturn(motorcycle);


        motorcycleService.saveMotorcycle(motorcycleAddDTO, userDetails);
        verify(userService).saveCurrentUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(1, capturedUser.getMyMotorcycles().size());

        Motorcycle capturedMotorcycle = capturedUser.getMyMotorcycles().get(0);

        assertEquals(motorcycle.getBrand(), capturedMotorcycle.getBrand());
        assertEquals(motorcycle.getModel(), capturedMotorcycle.getModel());
        assertEquals(motorcycle.getPrice(), capturedMotorcycle.getPrice());
        assertEquals(motorcycle.getProductionDate(), capturedMotorcycle.getProductionDate());
        assertEquals(motorcycle.getPhotosUrls(), capturedMotorcycle.getPhotosUrls());
        assertEquals(motorcycle.getRegisteredOn(), capturedMotorcycle.getRegisteredOn());
        assertEquals(motorcycle.getOwner(), capturedMotorcycle.getOwner());
    }

    @Test
    void theMostExpensiveMotorcycle() {
        Motorcycle expensiveMotorcycle = new Motorcycle();
        expensiveMotorcycle.setPhotosUrls(List.of("photo1", "photo2"));

        TheMostExpensiveVehicleView view = new TheMostExpensiveVehicleView();
        view.setPhotosUrls(List.of("photo1", "photo2"));

        when(motorcycleRepository.findMostExpensiveMotorcycles()).thenReturn(List.of(expensiveMotorcycle));
        when(modelMapper.map(expensiveMotorcycle, TheMostExpensiveVehicleView.class)).thenReturn(view);

        TheMostExpensiveVehicleView result = motorcycleService.theMostExpensiveMotorcycle();

        assertNotNull(result);
        assertEquals(view.getPhotosUrls(), result.getPhotosUrls());
    }

    @Test
    void findLatestMotorcycles() {
        Motorcycle latestMotorcycle = new Motorcycle();
        latestMotorcycle.setModel("Latest");
        latestMotorcycle.setProductionDate(LocalDateTime.now());
        latestMotorcycle.setPrice(BigDecimal.valueOf(5000.00));
        VehicleView vehicleView = new VehicleView()
                .setModel("Latest")
                .setProductionDate(ModelAttributeUtil.formatDate(latestMotorcycle.getProductionDate()))
                .setPrice(ModelAttributeUtil.formatPrice(latestMotorcycle.getPrice()))
                .setType("motorcycle");

        when(motorcycleRepository.findLatestMotorcycles()).thenReturn(List.of(latestMotorcycle));
        when(modelMapper.map(latestMotorcycle, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = motorcycleService.findLatestMotorcycles();

        assertFalse(result.isEmpty());
        assertEquals(vehicleView.getModel(), result.get(0).getModel());
        assertEquals(vehicleView.getPrice(), result.get(0).getPrice());
        assertEquals(vehicleView.getProductionDate(), result.get(0).getProductionDate());
    }

    @Test
    void findById() {
        when(motorcycleRepository.findById(anyLong())).thenReturn(Optional.of(motorcycle));

        Motorcycle result = motorcycleService.findById(1L);

        assertNotNull(result);
        assertEquals(motorcycle.getModel(), result.getModel());
    }

    @Test
    void findMotorcycleOwner() {
        when(motorcycleRepository.findById(anyLong())).thenReturn(Optional.of(motorcycle));

        User result = motorcycleService.findMotorcycleOwner(1L);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void availableMotorcycles() {
        when(motorcycleRepository.count()).thenReturn(1L);

        boolean result = motorcycleService.availableMotorcycles();

        assertTrue(result);
    }

    @Test
    void testRemoveMotorcycleFromFavorites() {
        UserFavoriteMotorcycle favoriteMotorcycle = new UserFavoriteMotorcycle();
        favoriteMotorcycle.setMotorcycle(motorcycle);
        user.getFavoriteMotorcycles().add(favoriteMotorcycle);
        when(userService.findAll()).thenReturn(List.of(user));

        motorcycleService.removeMotorcycleFromFavorites(motorcycle);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveCurrentUser(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(0, updatedUser.getFavoriteMotorcycles().size());
    }

    @Test
    void saveVehicle() {
        Vehicle vehicle = new Motorcycle();
        when(modelMapper.map(vehicle, Motorcycle.class)).thenReturn(motorcycle);

        motorcycleService.saveVehicle(vehicle);

        verify(motorcycleRepository).save(motorcycle);
    }

    @Test
    void testFindMyFavoriteMotorcycles() {
        Motorcycle favoriteMotorcycle = new Motorcycle();
        favoriteMotorcycle.setModel("Ninja");
        favoriteMotorcycle.setPrice(BigDecimal.valueOf(15000.00));
        favoriteMotorcycle.setProductionDate(LocalDateTime.now());

        UserFavoriteMotorcycle favoriteMotorcycleEntity = new UserFavoriteMotorcycle();
        favoriteMotorcycleEntity.setMotorcycle(favoriteMotorcycle);
        favoriteMotorcycleEntity.setAddedToFavorite(LocalDateTime.now());

        user.setFavoriteMotorcycles(Collections.singletonList(favoriteMotorcycleEntity));

        VehicleView vehicleView = new VehicleView();
        vehicleView.setModel("Ninja");
        vehicleView.setPrice(ModelAttributeUtil.formatPrice(favoriteMotorcycle.getPrice()));
        vehicleView.setProductionDate(ModelAttributeUtil.formatDate(favoriteMotorcycle.getProductionDate()));
        vehicleView.setAddedToFavorite(favoriteMotorcycleEntity.getAddedToFavorite());
        vehicleView.setType("motorcycle");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(favoriteMotorcycle, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = motorcycleService.findMyFavoriteMotorcycles("testUser");

        assertFalse(result.isEmpty());
        assertEquals(vehicleView, result.get(0));

        verify(modelMapper).map(favoriteMotorcycle, VehicleView.class);
    }

    @Test
    void findMyMotorcycles() {
        VehicleView vehicleView = new VehicleView()
                .setModel("Ninja")
                .setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()))
                .setProductionDate(ModelAttributeUtil.formatDate(motorcycle.getProductionDate()))
                .setType("motorcycle");
        user.setMyMotorcycles(Collections.singletonList(motorcycle));
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(motorcycle, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = motorcycleService.findMyMotorcycles("testUser");

        assertFalse(result.isEmpty());
        assertEquals(vehicleView.getModel(), result.get(0).getModel());
        assertEquals(vehicleView.getPrice(), result.get(0).getPrice());
    }

    @Test
    void refreshMotorcycle() {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setRegisteredOn(LocalDateTime.of(2020, 1, 1, 0, 0));

        when(motorcycleRepository.findById(1l)).thenReturn(Optional.of(motorcycle));

        motorcycleService.refreshMotorcycle(1l);

        verify(motorcycleRepository).findById(1l);
        verify(motorcycleRepository).save(motorcycle);
        assertTrue(motorcycle.getRegisteredOn().isAfter(LocalDateTime.of(2020, 1, 1, 0, 0)));
    }

    @Test
    void createMotorcycleProfileView() {
        motorcycle.setRegisteredOn(LocalDateTime.of(2023, 8, 3, 12, 0));

        MotorcycleProfileView motorcycleProfileView = new MotorcycleProfileView();
        motorcycleProfileView.setModel("Ninja");
        motorcycleProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(motorcycle.getRegisteredOn()));
        motorcycleProfileView.setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()));

        when(modelMapper.map(motorcycle, MotorcycleProfileView.class)).thenReturn(motorcycleProfileView);

        MotorcycleProfileView result = motorcycleService.createMotorcycleProfileView(motorcycle);

        assertNotNull(result);
        assertEquals(motorcycle.getModel(), result.getModel());
        assertEquals(ModelAttributeUtil.formatDate(motorcycle.getRegisteredOn()), result.getRegisteredOn());
        assertEquals(ModelAttributeUtil.formatPrice(motorcycle.getPrice()), result.getPrice());

        verify(modelMapper).map(motorcycle, MotorcycleProfileView.class);
    }

    @Test
    void addFavoriteMotorcycle() {

        motorcycleService.addFavoriteMotorcycle(motorcycle, user);

        assertEquals(1, user.getFavoriteMotorcycles().size());

        UserFavoriteMotorcycle favoriteMotorcycle = user.getFavoriteMotorcycles().get(0);
        assertNotNull(favoriteMotorcycle);
        assertEquals(motorcycle, favoriteMotorcycle.getMotorcycle());
        assertEquals(user, favoriteMotorcycle.getUser());
        assertNotNull(favoriteMotorcycle.getAddedToFavorite());
    }

    @Test
    void deleteFavoriteMotorcycle() {

        UserFavoriteMotorcycle favoriteMotorcycle = new UserFavoriteMotorcycle()
                .setMotorcycle(motorcycle)
                .setAddedToFavorite(LocalDateTime.now())
                .setUser(user);

        user.setFavoriteMotorcycles(new ArrayList<>(List.of(favoriteMotorcycle)));

        motorcycleService.deleteFavoriteMotorcycle(motorcycle, user);

        assertTrue(user.getFavoriteMotorcycles().isEmpty());
    }

    @Test
    void deleteMotorcycle() {
        user.getMyMotorcycles().add(motorcycle);
        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        doNothing().when(motorcycleRepository).deleteById(anyLong());

        motorcycleService.deleteMotorcycle(user, 1L, motorcycle);

        assertFalse(user.getMyMotorcycles().contains(motorcycle));
        verify(motorcycleRepository).deleteById(1L);
    }
}