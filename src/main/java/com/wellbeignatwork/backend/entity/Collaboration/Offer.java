package com.wellbeignatwork.backend.entity.Collaboration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Offer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long  idOffer;
	String title;
	String descrption;
	LocalDateTime starDateOf;
	LocalDateTime endDateOf;
	int nplaces;
	float promotion;
	int percentage;
	String localisation;
	float prix;

	@Enumerated(EnumType.STRING)
	Happy happy;

	@JsonIgnore
	@ManyToOne
	Collaboration collaboration;

	@OneToMany(mappedBy="offers", cascade=CascadeType.ALL)
	private Set<Publicity>  publicity;

	@OneToOne(cascade = CascadeType.REMOVE)
	Image imagesOffer;
}
