package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temperaturi")
public class Temperature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Transient
	@JsonIgnore
	private Integer idOras;

	@Column(name = "valoare")
	private Double valoare;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_oras", nullable = false)
	@JsonIgnore
	private City city;

	@PrePersist
	public void prePersist() {
		timestamp = LocalDateTime.now();
	}
}
