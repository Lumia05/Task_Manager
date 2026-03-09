# 📋 LIVRABLES EXAMEN - Attentes Minimales

## QUESTION 1 - Conception Architecturale et Modélisation

**À fournir:**
1. Reformulation du besoin (acteurs, fonctionnalités, contraintes)
2. Modèle de données (tables, relations, clés)
3. Structure REST API (routes HTTP, codes réponse)
4. Architecture app (controllers/services/repos, SOLID, Clean Code)

**Minimum attendu:**
- 3-4 entités JPA avec relations
- 6-8 endpoints REST documentés
- Séparation controller/service/repository
- Justification architecture choisie

---

## QUESTION 2 - Réalisation, Qualité et Sécurité

**À fournir:**
1. Authentification sécurisée (JWT ou Sanctum)
2. CRUD complet des tâches
3. Validation stricte des données
4. Gestion centralisée des erreurs
5. Pagination

**Tests:**
- Couverture minimum **60%**
- Tests unitaires + intégration
- Tests d'authentification
- Tests CRUD

**Code:**
- Pas de duplication
- Méthodes courtes (<20 lignes)
- Clean Code (nommage, commentaires)

---

## QUESTION 3 - Conception du Pipeline

**À fournir:**
1. Stratégie de branches (GitFlow ou simplifiée)
2. Déclencheurs du pipeline (push, PR, tags)
3. Étapes du pipeline (liste des 7 étapes)

**7 Étapes Pipeline:**
1. Build (Maven/npm)
2. Lint (checkstyle)
3. Tests unitaires
4. SonarQube analysis
5. Scan sécurité (CVE)
6. Build Docker
7. Deploy

**Minimum:**
- Document expliquant stratégie
- Schéma ou description textuelle

---

## QUESTION 4 - Implémentation du Pipeline

**À fournir:**
1. Fichier pipeline (GitHub Actions ou GitLab CI)
2. Intégration SonarQube/SonarCloud
3. Scan sécurité (Trivy, audit npm/composer)
4. Détection de secrets

**Minimum:**
- Pipeline `.yml` fonctionnel
- Couverture JaCoCo reportée
- SonarCloud configuré
- Trivy ou équivalent

---

## QUESTION 5 - Déploiement Automatique

**À fournir:**
1. Build Docker automatique
2. Push vers registry (Docker Hub, GHCR)
3. Déploiement (docker-compose ou K8s)
4. Redémarrage automatique

**Minimum:**
- Dockerfile produit
- docker-compose.yaml
- Auto-redeploy sur main branch

---

## QUESTION 6 - Optimisation et Clean Code

**À fournir:**
1. Architecture modulaire / composants
2. Optimisation pipeline (cache, parallelizing)
3. Guide exécution + correction erreurs
4. Documentation SonarQube quality gate

**Minimum:**
- Explainer pourquoi architecture est bonne
- Tips optimisation (+5% temps gain)
- README avec troubleshooting

---

## 📦 LIVRABLES OBLIGATOIRES

```
✅ Code Source (GitHub)
   ├─ Controllers REST (>6 endpoints)
   ├─ Services (logique métier)
   ├─ Repositories (JPA)
   ├─ Tests (>60% couverture)
   └─ Configuration (pom.xml, application.properties)

✅ Pipeline CI/CD
   ├─ .github/workflows/*.yml (ou .gitlab-ci.yml)
   ├─ SonarCloud/SonarQube config
   └─ Scan sécurité (Trivy)

✅ Docker
   ├─ Dockerfile (production-ready)
   └─ docker-compose.yaml

✅ Documentation
   ├─ README (API + architecture)
   └─ Justification choix techniques
```

---

## 🎯 CRITÈRES D'ÉVALUATION

| Critère | Points | Minimum |
|---------|--------|---------|
| **Question 1** - Architecture | 20 | 15/20 |
| **Question 2** - Réalisation + Tests | 20 | 15/20 |
| **Question 3** - Design Pipeline | 15 | 12/15 |
| **Question 4** - Implémentation | 15 | 12/15 |
| **Question 5** - Déploiement | 15 | 12/15 |
| **Question 6** - Optimisation | 15 | 12/15 |
| **Total** | **100** | **78/100** |

---

## 🔑 POINTS CLÉS JURY

1. **Authentification JWT** - Tokens, signatures, validation
2. **Tests >60%** - AuthTest + TaskTest
3. **SonarCloud** - Configuration + rapports
4. **Pipeline** - 7 étapes fonctionnelles
5. **Docker** - Dockerfile + docker-compose
6. **Sécurité** - MDP hashed, JWT, validation input

---

## 📝 POUR LA DÉMO (5-10 min)

Montrer:
1. Code architecture (controller/service/repo)
2. Deploy via `mvn spring-boot:run`
3. API fonctionnelle (GET/POST/PUT/DELETE)
4. Tests passent (`mvn clean test`)
5. SonarCloud report
6. GitHub Actions pipeline
