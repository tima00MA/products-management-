# Application Web de Gestion des Produits (Spring Boot + MVC + Security)

Ce projet est une application web complète pour la gestion de produits, développée en Java avec **Spring Boot**, intégrant une interface utilisateur via **Thymeleaf** et une sécurité robuste avec **Spring Security**. Il permet des opérations CRUD sur les produits, une authentification sécurisée, et un contrôle d'accès basé sur les rôles.

---

## Technologies utilisées

- **Java** 17+
- **Spring Boot** (framework principal)
- **Spring MVC** (gestion des requêtes web)
- **Spring Security** (authentification et autorisation)
- **Spring Data JPA** (persistance des données)
- **Thymeleaf** (moteur de templates)
- **Bootstrap 5** (via WebJars, pour le style)
- **H2 Database** (base en mémoire, modifiable vers MySQL)
- **Maven** (gestion des dépendances)

---

## Fonctionnalités principales

- **Authentification sécurisée** avec Spring Security
- Gestion des rôles : `USER` et `ADMIN`
- **Opérations CRUD** sur les produits :
  - Créer, lire, mettre à jour, supprimer
- Interface web dynamique avec **Thymeleaf**
- Design responsive avec **Bootstrap 5**
- **Pagination** pour la liste des produits
- Affichage conditionnel selon les rôles
- Gestion des erreurs (ex. : accès refusé)
- Déconnexion sécurisée

---

## Sécurité avec Spring Security

Le système de sécurité est configuré via une classe Java annotée `@Configuration`, utilisant **Spring Security**.

### Configuration des accès

| Route                | Accès réservé à                   |
|----------------------|-----------------------------------|
| `/user/**`           | Rôle `USER`                       |
| `/admin/**`          | Rôle `ADMIN`                      |
| `/public/**`         | Public (aucune authentification)  |
| `/webjars/**`        | Public (ressources statiques)     |
| `/login`             | Page de connexion (public)        |
| `/logout`            | Déconnexion sécurisée             |
| `/notAuthorized`     | Page d'erreur pour accès refusé   |

### Utilisateurs en mémoire

Pour simplifier, les utilisateurs sont définis en mémoire avec `InMemoryUserDetailsManager` et les mots de passe sont encodés avec `BCryptPasswordEncoder`.

```java
@Bean
public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build(),
        User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
        User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build()
    );
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### Configuration de `SecurityFilterChain`

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/**").hasRole("USER")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/public/**", "/webjars/**").permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(e -> e
            .accessDeniedPage("/notAuthorized")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );
    return http.build();
}
```

---

## Lancer le projet localement

### 1. Cloner le dépôt

```bash
git clone https://github.com/<votre-utilisateur>/gestion-produits-springmvc.git
cd gestion-produits-springmvc
```

### 2. Importer dans un IDE

Ouvrir le projet dans **IntelliJ IDEA** ou **Eclipse**.

### 3. Lancer l'application

Exécuter via Maven :

```bash
mvn spring-boot:run
```

Ou lancer directement la classe principale `BdccEnsetSpringMvcApplication.java`.

### 4. Accéder à l'application

Ouvrir un navigateur et aller à :

```url
http://localhost:8080
```

---

## Comptes par défaut

| Nom d’utilisateur | Mot de passe | Rôle(s)         |
|-------------------|--------------|-----------------|
| `user1`           | `1234`       | `USER`          |
| `user2`           | `1234`       | `USER`          |
| `admin`           | `1234`       | `USER`, `ADMIN` |

---

## Structure du projet

```bash
src/
 └── main/
     ├── java/
     │   └── net/fatima/bdccensetspringmvc/
     │       ├── BdccEnsetSpringMvcApplication.java
     │       ├── entities/        # Entité Product.java
     │       ├── repository/      # Interface ProductRepository.java
     │       ├── web/             # Contrôleurs MVC
     │       └── sec/             # Configuration de sécurité
     └── resources/
         ├── templates/           # Pages HTML Thymeleaf
         ├── static/              # Ressources statiques (CSS, JS)
         └── application.properties
```

---

## Base de données

Par défaut, le projet utilise une base **H2** en mémoire. Pour passer à **MySQL**, modifier `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_produits
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## Interface utilisateur

- **Thymeleaf** : templates dynamiques
- **Bootstrap 5** : design responsive
- Menu contextuel selon le rôle de l'utilisateur
- Liste des produits dans un tableau paginé
- Boutons d'action pour créer, modifier, supprimer
- Messages d'erreur clairs pour les accès non autorisés

---


## Auteur

**Fatima**  
Mini-projet réalisé dans le cadre d'une formation sur **Spring MVC**.

