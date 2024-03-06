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
@Table(name = "orase")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Transient
	private Integer idTara;

	@Column(name = "nume_oras")
	private String nume;

	@Column(name = "latitudine")
	private Double lat;

	@Column(name = "longitudine")
	private Double lon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tara", nullable = false)
	@JsonIgnore
	private Country country;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "city", orphanRemoval = true)
	private List<Temperature> temperatures = new ArrayList<>();
}
