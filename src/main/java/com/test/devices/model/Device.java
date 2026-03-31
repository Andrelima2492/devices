package com.test.devices.model;



import com.test.devices.util.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String brand;
    @Enumerated(EnumType.STRING)
    private State state;
    @NotNull
    private  Integer creationTime;

    public Device(String name, String brand, State state, Integer creationTime){
        this.name=name;
        this.brand=brand;
        this.state=state;
        this.creationTime=creationTime;
    }
}
