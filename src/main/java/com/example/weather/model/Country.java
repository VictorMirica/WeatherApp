package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tari")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "nume_tara")
  private String nume;

  @Column(name = "latitudine")
  private Double lat;

  @Column(name = "longitudine")
  private Double lon;

  @JsonIgnore
  @ToString.Exclude
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country", orphanRemoval = true)
  private List<City> cities = new ArrayList<>();
}
