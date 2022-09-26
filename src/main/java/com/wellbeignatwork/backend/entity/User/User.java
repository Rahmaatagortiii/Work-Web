package com.wellbeignatwork.backend.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.entity.Collaboration.Profession;
import com.wellbeignatwork.backend.entity.Collaboration.Reservation;
import com.wellbeignatwork.backend.entity.Evaluation.Badge;
import com.wellbeignatwork.backend.entity.Evaluation.UserGift;
import com.wellbeignatwork.backend.entity.Event.Event;
import com.wellbeignatwork.backend.entity.Event.Subscription;
import com.wellbeignatwork.backend.entity.Forum.Opinion;
import com.wellbeignatwork.backend.entity.Forum.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the user database table.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 65981149772133526L;

	public User(String displayName, String password){
		this.displayName=displayName;
		this.password=password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String firstName;
	private String lastName;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	private String email;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled = false;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "created_date", nullable = false, updatable = false)

	protected LocalDateTime createdDate;


	protected LocalDateTime modifiedDate;

	private String password;

	private String provider;
	private String fireBaseToken;
	private int banDuration=3;
	private Date bannStartDate;
	private int badWordsCount=0;

	// bi-directional many-to-many association to Role
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_rolee", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	@JsonIgnore
	@ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
	private Set<ChatRoom> rooms;
	@JsonIgnore
	@OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
	private Set<Message>messages;
	private int pointFidelite;
	@Enumerated(EnumType.STRING)
	private Departement departement;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "userTags",joinColumns = @JoinColumn(name = "idUser"))
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Tags> userTags;
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Event> events;
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Set<Subscription> subscriptions;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Post> posts;
	@JsonIgnore
	@OneToMany(mappedBy = "idUser",fetch = FetchType.EAGER)
	private Set<UserGift> CadeauUser;

	@Enumerated(EnumType.STRING)
	private Badge badge;
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Set<Opinion> opinions;
	private String picture;

	@Enumerated(EnumType.STRING)
	private Profession profession;
	@OneToMany(mappedBy = "userRes")
	Set<Reservation> reservations;









}