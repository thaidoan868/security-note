package com.conguyetduong.securitynote.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
public class Note {

	// Can't be set by the builder
	// getter only
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;

	// Optional fields
	@Column(nullable = false)
	@NotBlank(message = "Title must not be blank")
	@Size(max = 255, message = "Title must be at most 255 characters.")
	private String title;

	@Size(max = 5000, message = "Content must be at most 5000 characters.")
	private String content;

	// Required fields
	@NotNull(message = "Owner is required")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	@Builder
	public Note(String title, String content, User owner) {
		this.title = (title == null || title.isBlank()) ? "Title" : title;
		this.content = content;
		this.owner = owner;
	}
}
