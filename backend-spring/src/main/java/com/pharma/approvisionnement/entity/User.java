/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.EnumType
 *  jakarta.persistence.Enumerated
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.Table
 *  jakarta.persistence.UniqueConstraint
 *  jakarta.validation.constraints.Email
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.Size
 */
package com.pharma.approvisionnement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users", uniqueConstraints={@UniqueConstraint(columnNames={"email_user"})})
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id;
    @NotBlank(message="Le nom est obligatoire")
    @Size(max=100)
    @Column(name="name_user", nullable=false)
    private @NotBlank(message="Le nom est obligatoire") @Size(max=100) String name;
    @NotBlank(message="L'email est obligatoire")
    @Email(message="Format d'email invalide")
    @Size(max=150)
    @Column(name="email_user", nullable=false, unique=true)
    private @NotBlank(message="L'email est obligatoire") @Email(message="Format d'email invalide") @Size(max=150) String email;
    @NotBlank(message="Le mot de passe est obligatoire")
    @Column(name="password", nullable=false)
    private @NotBlank(message="Le mot de passe est obligatoire") String password;
    @Column(name="role", nullable=false)
    @Enumerated(value=EnumType.STRING)
    private Role role;
    @Column(name="nom_grossiste")
    private String nomGrossiste;

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public User(Long id, String name, String email, String password, Role role, String nomGrossiste) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nomGrossiste = nomGrossiste;
    }

    public User() {
        this.role = User.$default$role();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) {
            return false;
        }
        Role this$role = this.getRole();
        Role other$role = other.getRole();
        if (this$role == null ? other$role != null : !((Object)((Object)this$role)).equals((Object)other$role)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        return !(this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste));
    }

    public String toString() {
        return "User(id=" + this.getId() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", role=" + String.valueOf((Object)this.getRole()) + ", nomGrossiste=" + this.getNomGrossiste() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        Role $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : ((Object)((Object)$role)).hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        return result;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public String getEmail() {
        return this.email;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected boolean canEqual(Object other) {
        return other instanceof User;
    }

    private static Role $default$role() {
        return Role.PHARMACIE;
    }

    public static enum Role {
        PHARMACIE,
        GROSSISTE;

    }

    public static class UserBuilder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private boolean role$set;
        private Role role$value;
        private String nomGrossiste;

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        UserBuilder() {
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", password=" + this.password + ", role$value=" + String.valueOf((Object)this.role$value) + ", nomGrossiste=" + this.nomGrossiste + ")";
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public User build() {
            Role role$value = this.role$value;
            if (!this.role$set) {
                role$value = User.$default$role();
            }
            return new User(this.id, this.name, this.email, this.password, role$value, this.nomGrossiste);
        }

        public UserBuilder role(Role role) {
            this.role$value = role;
            this.role$set = true;
            return this;
        }

        public UserBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
    }
}

