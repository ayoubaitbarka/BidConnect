# AI-SERVICE (RAG)

Service d'Intelligence Artificielle basé sur RAG (Retrieval Augmented Generation) pour la plateforme BidConnect.

## 🎯 Fonctionnalités

- **Ingestion de documents** : Chargement, découpage et indexation de documents
- **Embeddings vectoriels** : Génération d'embeddings via OpenAI
- **Base vectorielle** : Stockage dans Qdrant pour recherche sémantique
- **Chat RAG** : Chatbot contextuel basé sur les documents ingérés
- **API REST** : Endpoints documentés via Swagger

## 🏗️ Architecture

### Stack Technique
- **Java 21** + **Spring Boot 3.3.6**
- **LangChain4j 0.34.0** : Framework RAG
- **Qdrant** : Base vectorielle (Docker)
- **PostgreSQL** : Métadonnées (Docker)
- **OpenAI** : LLM et embeddings

### Structure
```
src/main/java/com/example/aiservice/
├── config/          # Configuration LangChain4j
├── controller/      # REST API
├── dto/             # Request/Response objects
├── entity/          # JPA entities
├── repository/      # Data access
└── service/         # Business logic
```

## 🚀 Démarrage

### Prérequis
- Java 21
- Docker & Docker Compose
- Clé API OpenAI

### 1. Démarrer l'infrastructure

```bash
docker-compose up -d
```

Cela démarre :
- **Qdrant** sur le port `6333`
- **PostgreSQL** sur le port `5433`

### 2. Configurer la clé OpenAI

```bash
export OPENAI_API_KEY="votre-clé-api"
```

Ou modifier `application.yml` :
```yaml
langchain4j:
  open-ai:
    chat-model:
      api-key: "votre-clé-api"
```

### 3. Compiler et lancer

```bash
./mvnw clean package
java -jar target/AI-SERVICE-0.0.1-SNAPSHOT.jar
```

Le service démarre sur **http://localhost:8085**

## 📚 Documentation API (Swagger)

Accéder à : **http://localhost:8085/swagger-ui.html**

### Endpoints principaux

#### 1. Ingestion de document
```http
POST /api/ai/ingest
Content-Type: application/json

{
  "documentId": "doc-123",
  "documentUrl": "http://localhost:8081/api/documents/doc-123/download"
}
```

**Réponse :**
```json
{
  "documentId": "doc-123",
  "status": "COMPLETED",
  "chunkCount": 42,
  "message": "Document successfully ingested"
}
```

#### 2. Chat RAG
```http
POST /api/ai/chat
Content-Type: application/json

{
  "query": "Quelles sont les conditions de l'appel d'offres ?",
  "conversationId": null
}
```

**Réponse :**
```json
{
  "answer": "Les conditions sont...",
  "sources": [],
  "conversationId": "uuid-123"
}
```

## 🔄 Flux RAG

### Ingestion
1. Téléchargement du document depuis `document-service`
2. Découpage en chunks (500 caractères, overlap 50)
3. Génération d'embeddings (OpenAI `text-embedding-3-small`)
4. Stockage dans Qdrant
5. Sauvegarde métadonnées dans PostgreSQL

### Chat
1. Réception de la question utilisateur
2. Génération embedding de la question
3. Recherche des 5 chunks les plus similaires (score > 0.7)
4. Construction du contexte
5. Génération de la réponse via GPT-3.5-turbo
6. Retour de la réponse + sources

## 🧪 Tests

### Test d'ingestion
```bash
curl -X POST http://localhost:8085/api/ai/ingest \
  -H "Content-Type: application/json" \
  -d '{
    "documentId": "test-doc",
    "documentUrl": "https://example.com/document.pdf"
  }'
```

### Test de chat
```bash
curl -X POST http://localhost:8085/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Résume le document",
    "conversationId": null
  }'
```

## 🐳 Docker

### Build de l'image
```bash
./mvnw clean package
docker build -t ai-service:latest .
```

### Lancement complet
```bash
docker-compose up -d
docker run -p 8085:8085 \
  -e OPENAI_API_KEY=your-key \
  --network host \
  ai-service:latest
```

## 🔧 Configuration

### Variables d'environnement
- `OPENAI_API_KEY` : Clé API OpenAI (obligatoire)
- `SPRING_DATASOURCE_URL` : URL PostgreSQL (défaut: `jdbc:postgresql://localhost:5433/ai_db`)
- `LANGCHAIN4J_QDRANT_HOST` : Hôte Qdrant (défaut: `localhost`)

### Paramètres RAG
Dans `application.yml` :
```yaml
langchain4j:
  qdrant:
    collection-name: bid_embeddings  # Nom de la collection
```

## 📊 Monitoring

### Vérifier Qdrant
```bash
curl http://localhost:6333/collections/bid_embeddings
```

### Vérifier PostgreSQL
```bash
docker exec -it ai-db psql -U ai_user -d ai_db
SELECT * FROM document_metadata;
```

## 🔗 Intégration avec les autres services

### Avec SOUMISSION-SERVICE
Le service peut être appelé après la soumission d'un dossier pour analyser la conformité.

### Avec DOCUMENT-SERVICE
Récupère les documents via l'URL de téléchargement.

## ⚠️ Limitations actuelles

- Pas d'authentification (à ajouter)
- Extraction des sources non implémentée
- Détection automatique du type MIME manquante
- Pas de gestion de la mémoire de conversation persistante

## 📝 TODO

- [ ] Ajouter sécurité (JWT)
- [ ] Implémenter extraction des sources
- [ ] Support multi-langues
- [ ] Cache des embeddings
- [ ] Métriques Prometheus

## 👥 Auteur

Développé pour le projet BidConnect
