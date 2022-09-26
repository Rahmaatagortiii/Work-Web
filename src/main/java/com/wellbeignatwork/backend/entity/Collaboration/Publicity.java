package com.wellbeignatwork.backend.entity.Collaboration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Publicity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long idPublicity;
	String title;
	String description;
	Date starDatePub;
	Date endDatePub;

	@JsonIgnore
	@ManyToOne
	Offer offers;

	@OneToOne(cascade = CascadeType.REMOVE)
	Image imagesPublicity;

	@Nullable
	String banner;
}