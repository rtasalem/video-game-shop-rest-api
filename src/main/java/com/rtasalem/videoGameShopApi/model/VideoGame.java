package com.rtasalem.videoGameShopApi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class VideoGame {
	
	@Id
	@SequenceGenerator(name = "GAME_ID_GEN", sequenceName = "GAME_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_ID_GEN")
	private int id;
	
	@NotBlank(message = "Video game title must not be left blank.")
	@Size(min = 1, max = 100, message = "Video game title must be greater than 1 character and less then 100 characters.")
	private String title;
	
	@NotBlank(message = "Video game genre must not be left blank.")
	@Size(min = 1, max = 100, message = "Video game genre must be greater than 1 character and less than 100 characters.")
	private String genre;
	
	@NotBlank(message = "Video game developer must not be left blank.")
	@Size(min = 5, max = 250, message = "Video game developer must be greater than 5 characters and less than 250 characters.")
	private String developer;
	
	@DecimalMax(value = "30.00", message = "Video game price must be less than £30.00 (GBP).")
	@DecimalMin(value = "5.00", message = "Video game price must be less than £5.00 (GBP).")
	private double price;

	public VideoGame() {
		super();
	}

	public VideoGame(String title, String genre, String developer, double price) {
		super();
		this.title = title;
		this.genre = genre;
		this.developer = developer;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
