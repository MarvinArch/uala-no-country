package com.noCountry.uala.security.entity;

import com.noCountry.uala.models.entity.Wallet;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor

public class Usuario {

		//Id de la tabla
		@Id

		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int idUsuario;

		@NotNull
		private String nombre;
		@NotNull
		private String apellido;
		@NotNull
		@Column(unique = true)
		private String nombreUsuario;
		@NotNull
		@Column(unique = true)
		private String email;
		@NotNull
		private String password;

		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "id_wallet")
		private Wallet wallet;


	@ManyToMany
	@JoinTable(
			name = "contactos",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "contacto_id")
	)
	private List<Usuario> contactos = new ArrayList<>();





	public Usuario(String nombre, String apellido, String nombreUsuario, String email, String password, Wallet wallet) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.wallet = wallet;


	}

	@NotNull
		@ManyToMany
		@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario"),
				inverseJoinColumns = @JoinColumn(name = "rol_id"))
		private Set<Rol> roles = new HashSet<>();

		public Usuario() {
		}


	public List<Usuario> getContactos() {
		return contactos;
	}

	public void setContactos(List<Usuario> contactos) {
		this.contactos = contactos;
	}


		public Usuario(@NotNull String nombre,
					   @NotNull String nombreUsuario,
					   @NotNull String email,
					   @NotNull String password) {
			this.nombre = nombre;
			this.nombreUsuario = nombreUsuario;
			this.email = email;
			this.password = password;
			this.wallet = wallet;
		}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getIdUsuario() {

			return idUsuario;
		}

		public void setIdUsuario(int idUsuario) {
			this.idUsuario = idUsuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getUsuario() {
			return nombreUsuario;
		}

		public void setUsuario(String usuario) {
			this.nombreUsuario = usuario;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Set<Rol> getRoles() {
			return roles;
		}

		public void setRoles(Set<Rol> roles) {
			this.roles = roles;
		}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}


}

