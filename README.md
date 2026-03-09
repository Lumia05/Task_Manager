# 📋 Task Manager - Application REST avec Pipeline CI/CD

Une application Spring Boot REST complète pour la gestion de tâches avec authentification JWT et un pipeline CI/CD automatisé.

## 📋 Table des Matières

- [Caractéristiques](#caractéristiques)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [API Documentation](#api-documentation)
- [Pipeline CI/CD](#pipeline-cicd)
- [Tests](#tests)
- [Sécurité](#sécurité)

## ✨ Caractéristiques

- ✅ **Authentification JWT** - Tokens sécurisés et stateless
- ✅ **CRUD Complet** - Gestion complète des tâches
- ✅ **Pagination** - Support de la pagination pour les requêtes
- ✅ **Validation des Données** - Validation stricte des entrées
- ✅ **Isolation des Données** - Chaque utilisateur n'accède qu'à ses tâches
- ✅ **Documentation Swagger/OpenAPI** - Documentation interactive de l'API
- ✅ **Tests Unitaires** - 60%+ de couverture de code
- ✅ **CI/CD Automatisé** - GitHub Actions avec SonarCloud
- ✅ **DevSecOps** - Scans de sécurité intégrés
- ✅ **Containerisation** - Docker et Docker Compose

## 🏗️ Architecture

```
Task_Manager
├── src/
│   ├── main/java/Task_Manager/
│   │   ├── controller/        # Contrôleurs REST
│   │   ├── service/           # Logique métier
│   │   ├── entity/            # Modèles JPA
│   │   ├── repository/        # Accès aux données
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── security/          # Configuration JWT & sécurité
│   │   ├── config/            # Configuration Spring
│   │   └── TaskManagerApplication.java
│   ├── test/                  # Tests unitaires & intégration
│   └── resources/
│       └── application.properties
├── .github/workflows/         # GitHub Actions CI/CD
├── Dockerfile                 # Image Docker multi-étages
├── compose.yaml              # Orchestration containers
└── pom.xml                   # Dépendances Maven
```

### Entités Principales

**User**
- ID, Username (unique), Email (unique), Password (hashed)
- Roles (relation many-to-many)

**Task**
- ID, Title, Description, Completed (booléen)
- CreatedAt, UpdatedAt (timestamps)
- UserId (propriétaire)

**Role**
- ID, Name (ROLE_USER, ROLE_ADMIN)

## 📦 Prérequis

- **Java 21+**
- **Maven 3.9+**
- **Docker & Docker Compose** (optionnel)
- **Git**
- **Compte GitHub** avec accès aux secrets

## 🚀 Installation

### 1️⃣ Cloner le dépôt

```bash
git clone https://github.com/yourusername/task-manager.git
cd task-manager
```

### 2️⃣ Installer les dépendances

```bash
mvn clean install
```

### 3️⃣ Configurer l'environnement

Créer un fichier `.env` (optionnel) :

```env
APP_JWT_SECRET=votre_secret_jwt_tres_long
APP_JWT_EXPIRATION_MS=86400000
```

## ⚙️ Configuration

### application.properties

```properties
# Server
server.port=8080

# Database (H2 In-Memory)
spring.datasource.url=jdbc:h2:mem:taskdb
spring.datasource.username=sa
spring.datasource.password=

# JWT
app.jwtSecret=mySecretKeyForTaskManagerApplicationThatIsLongEnoughForHS256Algorithm
app.jwtExpirationMs=86400000

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

## 🎯 Utilisation

### Lancer l'application

```bash
# Développement
mvn spring-boot:run

# Ou avec Docker
docker-compose up -d
```

L'application est disponible à `http://localhost:8080`

### Documentation OpenAPI

```
http://localhost:8080/swagger-ui.html
```

## 📚 API Documentation

### Authentification

#### 1. Enregistrement

```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!"
}

Response (201 Created):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john_doe",
  "email": "john@example.com",
  "message": "User registered successfully"
}
```

#### 2. Connexion

```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePassword123!"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john_doe",
  "email": "john@example.com",
  "message": "Login successful"
}
```

### Tâches

#### 3. Créer une tâche

```bash
POST /api/tasks
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Acheter les courses",
  "description": "Lait, pain, œufs"
}

Response (200 OK):
{
  "id": 1,
  "title": "Acheter les courses",
  "description": "Lait, pain, œufs",
  "completed": false,
  "createdAt": "2026-03-09T10:30:00",
  "updatedAt": null
}
```

#### 4. Lister les tâches (paginé)

```bash
GET /api/tasks?page=0&size=10
Authorization: Bearer {token}

Response (200 OK):
{
  "content": [ ... ],
  "pageable": { ... },
  "totalElements": 5,
  "totalPages": 1
}
```

#### 5. Récupérer une tâche

```bash
GET /api/tasks/{id}
Authorization: Bearer {token}
```

#### 6. Mettre à jour une tâche

```bash
PUT /api/tasks/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Titre modifié",
  "description": "Nouvelle description"
}
```

#### 7. Marquer comme complétée

```bash
PATCH /api/tasks/{id}/toggle
Authorization: Bearer {token}
```

#### 8. Supprimer une tâche

```bash
DELETE /api/tasks/{id}
Authorization: Bearer {token}
```

## 🔄 Pipeline CI/CD

### Étapes du Pipeline

```
┌─────────────┐
│  Push Code  │
└──────┬──────┘
       │
       ├──→ Checkout Code
       │
       ├──→ Setup Java 21
       │
       ├──→ Maven Build & Tests
       │
       ├──→ Generate Coverage (JaCoCo)
       │
       ├──→ SonarCloud Analysis
       │
       ├──→ Security Scan (Trivy)
       │
       ├──→ Build Docker Image
       │
       └──→ Deploy (main only)
```

### Configuration GitHub Secrets

Ajouter les secrets GitHub:

1. **SONAR_TOKEN** - Token SonarCloud
2. **GITHUB_TOKEN** - Token GitHub (auto-généré)

### Déclenchers du Pipeline

- ✅ Push sur `main` ou `develop`
- ✅ Pull Requests
- ✅ Dispatch manuel

### Rapports Générés

- **JaCoCo Coverage Report** - Couverture de code
- **SonarCloud Report** - Qualité, bugs, dette technique
- **Trivy Scan** - Vulnérabilités Docker
- **GitHub Security** - Sarif format

## ✅ Tests

### Exécuter les tests

```bash
# Tous les tests
mvn test

# Tests spécifiques
mvn test -Dtest=AuthControllerTest

# Avec rapport de couverture
mvn test jacoco:report
```

### Couverture de code

```bash
# Générer le rapport
mvn jacoco:report

# Consulter le rapport
open target/site/jacoco/index.html
```

### Classes de tests

- **AuthControllerTest** - Authentification (register, login)
- **TaskControllerTest** - Gestion des tâches (CRUD)

### Cas de test couverts

✅ Enregistrement d'utilisateur
✅ Cas d'erreur (username dupliquer, email existe)
✅ Connexion réussie / échouée
✅ Création de tâche
✅ Récupération de tâches
✅ Modification de tâche
✅ Suppression de tâche
✅ Toggle complétée
✅ Contrôle d'accès (401 Unauthorized)

## 🔒 Sécurité

### Authentification

- ✅ **JWT Tokens** - Tokens sans état avec signature HMAC-SHA256
- ✅ **Expiration** - 24 heures par défaut
- ✅ **Password Hashing** - BCrypt avec salt aléatoire
- ✅ **Validation Token** - Vérification signature à chaque requête

### Autorisation

- ✅ **Isolation des données** - Les utilisateurs ne voient que leurs tâches
- ✅ **Contrôle d'accès** - Endpoints protégés par JWT
- ✅ **CORS configuré** - Accessible depuis le frontend

### Protection

- ✅ **CSRF Disabled** - API stateless (pas de session)
- ✅ **Input Validation** - Validation stricte des DTOs
- ✅ **Injection Prevention** - JPA Parameterized Queries
- ✅ **Error Handling** - Messages d'erreur génériques

### DevSecOps

- ✅ **Dependency Scanning** - Maven/npm audit
- ✅ **Container Scanning** - Trivy image scan
- ✅ **Secret Detection** - TruffleHog integration
- ✅ **Code Quality** - SonarCloud rules

## 🐳 Conteneurisation

### Build Docker

```bash
# Depuis le Dockerfile (multi-stage)
docker build -t task-manager:latest .

# Ou avec compose
docker-compose build
```

### Lancer avec Docker Compose

```bash
docker-compose up -d

# Logs
docker-compose logs -f task-manager

# Arrêter
docker-compose down
```

### Health Check

L'application inclut un health check via `/actuator/health`

## 📊 Métriques & Monitoring

### Actuator Endpoints

```
http://localhost:8080/actuator/health      # Status
http://localhost:8080/actuator/metrics      # Métriques
```

## 🛠️ Troubleshooting

### Application ne démarre pas

```bash
# Vérifier Java
java -version  # Doit être 21+

# Vérifier les logs
mvn spring-boot:run | grep ERROR

# Vérifier le port
lsof -i :8080
```

### Tests échouent

```bash
# Nettoyer et reconstruire
mvn clean test

# Tests spécifiques
mvn test -Dtest=AuthControllerTest
```

### Erreur JWT

```
401 Unauthorized: Missing or invalid JWT token

# Solution: Ajouter header Authorization
Authorization: Bearer {token}
```

### Erreur SonarCloud

```bash
# Vérifier les secrets GitHub
# SONAR_TOKEN doit être configuré

# Ou relancer manuellement
mvn sonar:sonar -Dsonar.login={token}
```

## 📝 Justification des Choix Techniques

### 1. Spring Boot 4.0.3
- Framework moderne et stable
- Autoconfigurations éprouvées
- Excellent écosystème

### 2. JWT pour l'authentification
- Stateless (idéal pour microservices)
- Auto-contenu (pas de requête DB à chaque fois)
- Standard de l'industrie

### 3. JPA/Hibernate
- ORM puissant et mature
- Requêtes type-safe avec JPQL
- Migrations faciles avec Flyway/Liquibase

### 4. H2 Database
- In-memory pour dev/test
- Pas de dépendance externe
- Peut être remplacé facilement

### 5. GitHub Actions
- Natif à GitHub
- Gratuit pour repos public
- Excellente intégration SonarCloud

### 6. SonarCloud
- Analyse qualité dans le cloud
- Intégration pull request
- Reports détaillés et tendances

### 7. Docker
- Reproductibilité garantie
- Déploiement simple
- Isolation complète

## 🤝 Contribution

1. Fork le dépôt
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT.

## 👨‍💻 Auteur

- **Lumière** - Développeur Full Stack

## 📞 Support

Pour toute question ou problème:
- Ouvrir une GitHub Issue
- Consulter la documentation SonarCloud
- Vérifier les logs GitHub Actions

---

**Dernière mise à jour:** 9 mars 2026
