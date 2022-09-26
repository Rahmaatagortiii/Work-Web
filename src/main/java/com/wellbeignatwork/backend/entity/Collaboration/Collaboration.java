package com.wellbeignatwork.backend.entity.Collaboration;

import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonIgnore;

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
public class Collaboration implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long idCollaboration;
	String name;
	String description;
	int phone;
	String email;
	Date date;
	String rate ;
	String town;
	

	@OneToMany(mappedBy="collaboration", cascade=CascadeType.ALL)
	private Set<Offer> offers;

	@OneToOne(cascade = CascadeType.REMOVE)
	Image imagesCollab;

	@JsonIgnore
	@ManyToOne
    User users;

}
