# 🔧 Configuration du Pipeline CI/CD

Ce document décrit comment configurer le pipeline CI/CD pour votre projet Task Manager.

## 📋 Table des matières

1. [Configuration GitHub](#configuration-github)
2. [Configuration SonarCloud](#configuration-sonarcloud)
3. [Secrets GitHub](#secrets-github)
4. [Déclencher le Pipeline](#déclencher-le-pipeline)
5. [Monitoring et Rapports](#monitoring-et-rapports)

---

## Configuration GitHub

### 1. Push Initial sur GitHub

```bash
# Initialiser Git (si pas déjà fait)
git init

# Ajouter tous les fichiers
git add .

# Premier commit
git commit -m "Initial commit: Task Manager API with CI/CD"

# Ajouter le dépôt distant
git remote add origin https://github.com/YOUR_USERNAME/task-manager.git

# Push vers GitHub
git branch -M main
git push -u origin main
```

### 2. Créer les Branches

```bash
# Créer branche develop
git checkout -b develop
git push -u origin develop

# Créer branche feature
git checkout -b feature/authentication
# Faites vos changements
git add .
git commit -m "feat: add JWT authentication"
git push origin feature/authentication

# Créer une Pull Request sur GitHub UI
```

### 3. Vérifier les Workflows

- Allez sur: `https://github.com/YOUR_USERNAME/task-manager/actions`
- Vous devriez voir le workflow `CI/CD Pipeline` en cours d'exécution

---

## Configuration SonarCloud

### Étape 1: Créer un compte SonarCloud

1. Allez sur https://sonarcloud.io
2. Cliquez sur "Sign up with GitHub"
3. Autorisez SonarCloud à accéder à vos dépôts

### Étape 2: Créer le Token SonarCloud

1. Allez sur https://sonarcloud.io/account/security
2. Cliquez sur "Generate Tokens"
3. Donnez un nom: `github-action-task-manager`
4. Copiez le token (vous le recopierez une seule fois)

### Étape 3: Ajouter le Dépôt à SonarCloud

1. Sur SonarCloud, allez à "My Account" → "My Organizations"
2. Cliquez sur votre organisation
3. Allez à "Projects"
4. Cliquez sur "Analyze new project"
5. Sélectionnez votre dépôt `task-manager`
6. Cliquez sur "Set up"

### Étape 4: Sélectionner la Méthode de Scanning

- Choisir: **Other CI** (car nous utilisons GitHub Actions)
- Copier le contenu suivant dans le pom.xml (déjà fait dans ce projet)

```xml
<properties>
    <sonar.projectKey>lumiere_task-manager</sonar.projectKey>
    <sonar.organization>lumiere</sonar.organization>
    <sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
```

---

## Secrets GitHub

### Comment Ajouter un Secret

1. Allez sur: `https://github.com/YOUR_USERNAME/task-manager/settings/secrets/actions`
2. Cliquez sur "New repository secret"
3. Remplissez le nom et la valeur
4. Cliquez "Add secret"

### Secrets Requis

#### 1. SONAR_TOKEN ⭐ **OBLIGATOIRE**

```
Name: SONAR_TOKEN
Value: [Votre token SonarCloud copié à l'étape précédente]
```

**Exemple:**
```
squ_1234567890abcdefghijklmnopqrstuvwxyz
```

### Secrets Supplémentaires (Optionnels)

#### GITHUB_TOKEN

- **Généré automatiquement** par GitHub
- N'a pas besoin d'être configuré manuellement
- Utilisé pour: publier des rapports, créer des issues, etc.

#### DOCKER_REGISTRY_TOKEN (Futur)

Pour pousser vers Docker Hub:
```
Name: DOCKER_REGISTRY_TOKEN
Value: [Votre token Docker Hub]
```

---

## Déclencher le Pipeline

### Déclencheurs Automatiques

Le pipeline se déclenche automatiquement sur:

1. **Push vers `main`**
   ```bash
   git push origin main
   ```

2. **Push vers `develop`**
   ```bash
   git push origin develop
   ```

3. **Pull Request**
   ```bash
   git push origin feature/my-feature
   # Créer PR sur GitHub UI
   ```

### Déclencher Manuellement

1. Allez sur: `https://github.com/YOUR_USERNAME/task-manager/actions`
2. Sélectionnez le workflow `CI/CD Pipeline`
3. Cliquez `Run workflow`
4. Sélectionnez la branche
5. Cliquez `Run workflow`

---

## Monitoring et Rapports

### 1. GitHub Actions

- **URL**: `https://github.com/YOUR_USERNAME/task-manager/actions`
- **Affiche**: Statut de chaque job, logs détaillés
- **Durée**: ~10-15 minutes pour la première exécution

### 2. SonarCloud Reports

- **URL**: `https://sonarcloud.io/project/overview?id=lumiere_task-manager`
- **Affiche**:
  - ✅ Code Quality (A-F)
  - 🐛 Bugs trouvés
  - 📚 Code Smells
  - 🔐 Vulnérabilités
  - 🎯 Couverture de code
  - 📊 Tendances sur le temps

### 3. Rapports de Couverture (JaCoCo)

- **Généré**: À chaque test
- **Localisation**: `target/site/jacoco/index.html`
- **Affiche**: Couverture par classe/méthode

### 4. Pull Request Comments

- SonarCloud commente **automatiquement** chaque PR
- Affiche les nouveaux problèmes de qualité
- Indique si le Quality Gate passe

---

## Dépannage

### Le Pipeline Ne S'exécute Pas

**Solution 1**: Vérifier les fichiers YAML

```bash
# Les fichiers doivent être dans:
# .github/workflows/ci-cd-pipeline.yml

# Vérifier le chemin exact:
ls -la .github/workflows/
```

**Solution 2**: Vérifier le nombre d'indentations YAML

```yaml
# ❌ Mauvais (indentation incorrecte)
jobs:
build:  # Mauvais!

# ✅ Correct
jobs:
  build:  # 2 espaces
```

### SonarCloud Ne Montre Rien

1. Vérifier `SONAR_TOKEN` dans GitHub Secrets
2. Vérifier les propriétés dans `pom.xml`:
   ```xml
   <sonar.projectKey>lumiere_task-manager</sonar.projectKey>
   <sonar.organization>lumiere</sonar.organization>
   ```
3. Ces valeurs doivent correspondre à votre compte SonarCloud

### Erreur: "SONAR_TOKEN not found"

```bash
# Vous avez oublié d'ajouter le secret!

# Vérifier que le secret est ajouté:
https://github.com/YOUR_USERNAME/task-manager/settings/secrets/actions

# Le nom doit être exacatement: SONAR_TOKEN
```

### Tests Échouent

1. Consulter les logs GitHub Actions
2. Reproduire localement:
   ```bash
   mvn clean test
   ```
3. Corriger les tests
4. Pusher nouveau commit

---

## Étapes Suivantes

Après la première exécution réussie:

1. ✅ Configurer les **Branch Protection Rules**
   ```
   Settings → Branches → Add Rule
   - Require PR reviews: 1
   - Require status checks to pass: ✓
   - Include administrators: ✓
   ```

2. ✅ Configurer le **Quality Gate** SonarCloud
   ```
   SonarCloud → Project Settings → Quality Gates
   - Reliability: A+
   - Security: A+
   - Coverage: >60%
   ```

3. ✅ Ajouter les **Badges** au README
   ```markdown
   [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lumiere_task-manager&metric=alert_status)](...)
   [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=lumiere_task-manager&metric=coverage)...
   ```

4. ✅ Configurer notifications:
   - Email pour les déceps
   - Slack/Discord pour les alertes

---

## Résumé de Configuration

| Élément | Valeur | Statut |
|--------|--------|--------|
| GitHub Repo | task-manager | ✅ |
| SonarCloud Project | lumiere_task-manager | ✅ |
| SONAR_TOKEN | [Configuré] | ✅ |
| GitHub Actions | .github/workflows/ci-cd-pipeline.yml | ✅ |
| Branches | main, develop | ✅ |
| Quality Gate | Configuré | ✅ |

---

**Dernière mise à jour:** 9 Mars 2026
