# PixelVerse Technologies — Microservices Platform
## ArtConnect Pvt. Ltd. | Java Full Stack Case Study

---

## 📐 Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                    CLIENT (Browser / Mobile)                    │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    ┌────────▼────────┐
                    │   API Gateway   │
                    │  (Centralized)  │
                    └──┬──┬──┬──┬────┘
           ┌──────────┘  │  │  └────────────┐
     ┌─────▼──────┐  ┌───▼──▼──┐  ┌────────▼────────┐  ┌────────────────┐
     │   Artist   │  │Portfolio │  │    License      │  │  Notification  │
     │  Service   │  │ Service  │  │    Service      │  │    Service     │
     │  Port 8081 │  │Port 8082 │  │   Port 8083     │  │   Port 8084    │
     └─────┬──────┘  └───┬──────┘  └────────┬────────┘  └────────┬───────┘
           │             │                  │                     │
     ┌─────▼──────┐ ┌────▼──────┐  ┌───────▼────────┐  ┌────────▼───────┐
     │PixelVerse  │ │PixelVerse │  │  PixelVerse    │  │  PixelVerse    │
     │  _Artist   │ │ _Portfolio│  │   _License     │  │ _Notification  │
     │  (MySQL)   │ │  (MySQL)  │  │   (MySQL)      │  │   (MySQL)      │
     └────────────┘ └───────────┘  └────────────────┘  └────────────────┘
```

---

## 🗂️ Project Structure

| Microservice | Port | Database | Description |
|---|---|---|---|
| **PixelVerse-Artist** | `8081` | `PixelVerse_Artist` | Artist/Client/Admin profiles, roles, bios |
| **PixelVerse-Portfolio** | `8082` | `PixelVerse_Portfolio` | Artworks, collections, file metadata |
| **PixelVerse-License** | `8083` | `PixelVerse_License` | License lifecycle, certificates, contracts |
| **PixelVerse-Notification** | `8084` | `PixelVerse_Notification` | Alerts, emails, unread tracking |

---

## 🗄️ Database Setup

Run in MySQL Workbench / CLI before starting:

```sql
CREATE DATABASE IF NOT EXISTS PixelVerse_Artist    CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS PixelVerse_Portfolio CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS PixelVerse_License   CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS PixelVerse_Notification CHARACTER SET utf8mb4;
```

> Full script: `PixelVerse-Artist/setup-databases.sql`

---

## 🔑 Tech Stack

- **Spring Boot 3.2.5** · **Spring Data JPA** · **Spring Web MVC**
- **MySQL 8.x** · **Spring Mail** · **Spring Validation**
- **Java 17** · **Maven**

---

## 🧩 Package Layout (Each Service)

```
com.example.demo
├── model/          ← JPA Entities
├── dto/            ← Request / Response DTOs
├── repository/     ← Spring Data JPA Repositories
├── service/        ← Business Logic
├── controller/     ← REST Controllers (@RestController)
└── exception/      ← GlobalExceptionHandler + Custom Exceptions
```

---

## 📌 Microservice 1 — Artist Service (Port 8081)

### Entity: `Artist`

| Field | Type | Notes |
|---|---|---|
| `id` | Long | Primary Key |
| `name` | String | Required |
| `email` | String | Unique |
| `password` | String | |
| `role` | Enum | `ARTIST`, `CLIENT`, `ADMIN` |
| `bio` | TEXT | Profile bio |
| `profileImageUrl` | String | S3/CDN URL |
| `specialization` | String | e.g., Illustration |
| `country` | String | |
| `website` | String | |
| `skills` | List\<String\> | @ElementCollection |
| `active` | boolean | Soft delete flag |
| `createdAt` | LocalDateTime | Auto-set |

### REST Endpoints

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/artists` | Register artist/client/admin |
| `GET` | `/api/artists` | Get all artists |
| `GET` | `/api/artists/active` | Get active artists only |
| `GET` | `/api/artists/{id}` | Get by ID |
| `GET` | `/api/artists/email/{email}` | Get by email |
| `GET` | `/api/artists/role/{role}` | Filter by role |
| `GET` | `/api/artists/specialization/{spec}` | Filter by specialization |
| `GET` | `/api/artists/country/{country}` | Filter by country |
| `PUT` | `/api/artists/{id}` | Update profile |
| `PATCH` | `/api/artists/{id}/deactivate` | Soft delete |
| `DELETE` | `/api/artists/{id}` | Hard delete |

### Sample Request — Create Artist
```json
POST /api/artists
{
  "name": "Priya Sharma",
  "email": "priya@artconnect.in",
  "password": "secure123",
  "role": "ARTIST",
  "bio": "Digital illustrator specializing in character art.",
  "specialization": "Digital Illustration",
  "country": "India",
  "website": "https://priyaart.com",
  "skills": ["Character Design", "Concept Art", "UI/UX"]
}
```

---

## 📌 Microservice 2 — Portfolio Service (Port 8082)

### Entity: `Artwork`

| Field | Type | Notes |
|---|---|---|
| `id` | Long | Primary Key |
| `title` | String | Required |
| `artistId` | Long | FK → Artist Service |
| `imageUrl` | String | S3/CDN URL |
| `thumbnailUrl` | String | |
| `resolution` | Enum | `LOW`, `MEDIUM`, `HIGH`, `ULTRA_HD` |
| `category` | Enum | `ILLUSTRATION`, `PHOTOGRAPHY`, `DIGITAL_ART`, etc. |
| `medium` | String | Digital, Oil, Watercolor |
| `fileFormat` | String | JPEG, PNG, SVG, PSD |
| `fileSizeMb` | Double | |
| `widthPx/heightPx` | Integer | Dimensions |
| `price` | Double | Licensing price |
| `forSale` | boolean | |
| `tags` | List\<String\> | @ElementCollection |
| `collection` | Collection | @ManyToOne |

### Entity: `Collection`

| Field | Type | Notes |
|---|---|---|
| `id` | Long | PK |
| `name` | String | Required |
| `artistId` | Long | FK → Artist Service |
| `artworks` | List\<Artwork\> | @OneToMany |
| `coverImageUrl` | String | |

### REST Endpoints — Artwork

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/artworks` | Upload artwork metadata |
| `GET` | `/api/artworks` | All artworks |
| `GET` | `/api/artworks/{id}` | Get by ID |
| `GET` | `/api/artworks/artist/{artistId}` | Artist's portfolio |
| `GET` | `/api/artworks/category/{category}` | By category |
| `GET` | `/api/artworks/resolution/{resolution}` | By resolution |
| `GET` | `/api/artworks/for-sale` | Available for licensing |
| `GET` | `/api/artworks/collection/{collectionId}` | Artworks in collection |
| `GET` | `/api/artworks/search?keyword=abc` | Title search |
| `PUT` | `/api/artworks/{id}` | Update artwork |
| `DELETE` | `/api/artworks/{id}` | Delete artwork |

### REST Endpoints — Collection

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/collections` | Create collection |
| `GET` | `/api/collections` | All collections |
| `GET` | `/api/collections/{id}` | By ID |
| `GET` | `/api/collections/artist/{artistId}` | Artist's collections |
| `GET` | `/api/collections/search?keyword=abc` | Search by name |
| `PUT` | `/api/collections/{id}` | Update |
| `DELETE` | `/api/collections/{id}` | Delete |

### Sample Request — Upload Artwork
```json
POST /api/artworks
{
  "title": "Midnight Bloom",
  "description": "Abstract digital painting in dark palette",
  "artistId": 1,
  "imageUrl": "https://s3.amazonaws.com/pixelverse/artworks/midnight-bloom.png",
  "thumbnailUrl": "https://s3.amazonaws.com/pixelverse/thumbs/midnight-bloom-thumb.png",
  "resolution": "ULTRA_HD",
  "category": "DIGITAL_ART",
  "medium": "Digital",
  "fileFormat": "PNG",
  "fileSizeMb": 12.5,
  "widthPx": 4096,
  "heightPx": 4096,
  "price": 2500.00,
  "forSale": true,
  "tags": ["abstract", "dark", "floral", "digital"],
  "collectionId": 1
}
```

---

## 📌 Microservice 3 — License Service (Port 8083)

### Entity: `License`

| Field | Type | Notes |
|---|---|---|
| `id` | Long | PK |
| `artworkId` | Long | FK → Portfolio Service |
| `artistId` | Long | FK → Artist Service |
| `clientId` | Long | FK → Artist Service (CLIENT role) |
| `licenseType` | Enum | `PERSONAL`, `COMMERCIAL`, `EXCLUSIVE`, `EDITORIAL`, `PRINT_ONLY`, `DIGITAL_ONLY` |
| `status` | Enum | `PENDING` → `APPROVED` → `ACTIVE` \| `REJECTED` \| `EXPIRED` \| `CANCELLED` |
| `price` | Double | |
| `startDate` | LocalDate | Set on approval |
| `expiryDate` | LocalDate | |
| `certificateUrl` | String | Auto-generated PDF URL |
| `usageTerms` | String | Custom terms |
| `territory` | String | Global, India, USA |
| `allowsModification` | boolean | |
| `allowsResale` | boolean | |
| `allowsAttribution` | boolean | |
| `maxUsageCount` | Integer | |

### License Status Flow

```
  [Client creates request]
        │
        ▼
    PENDING  ──(reject)──► REJECTED
        │
    (approve)
        │
        ▼
    APPROVED ──(activate/payment)──► ACTIVE ──(expiry date passed)──► EXPIRED
        │                               │
     (cancel)                        (cancel)
        │                               │
        ▼                               ▼
   CANCELLED                       CANCELLED
```

### REST Endpoints

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/licenses` | Create license request |
| `GET` | `/api/licenses` | All licenses |
| `GET` | `/api/licenses/{id}` | By ID |
| `GET` | `/api/licenses/client/{clientId}` | Client's licenses |
| `GET` | `/api/licenses/artist/{artistId}` | Artist's licenses |
| `GET` | `/api/licenses/artwork/{artworkId}` | Artwork's licenses |
| `GET` | `/api/licenses/status/{status}` | Filter by status |
| `GET` | `/api/licenses/type/{type}` | Filter by type |
| `GET` | `/api/licenses/expired` | All expired licenses |
| `GET` | `/api/licenses/expiring?days=30` | Expiring soon |
| `PATCH` | `/api/licenses/{id}/approve` | Artist approves |
| `PATCH` | `/api/licenses/{id}/reject` | Artist rejects |
| `PATCH` | `/api/licenses/{id}/activate` | Activate after payment |
| `PATCH` | `/api/licenses/{id}/cancel` | Cancel license |
| `GET` | `/api/licenses/{id}/certificate` | Get certificate URL |
| `PUT` | `/api/licenses/{id}` | Update license terms |
| `DELETE` | `/api/licenses/{id}` | Delete |

### Sample Request — License Request
```json
POST /api/licenses
{
  "title": "Midnight Bloom — Commercial License",
  "artworkId": 1,
  "artistId": 1,
  "clientId": 5,
  "licenseType": "COMMERCIAL",
  "price": 5000.00,
  "expiryDate": "2027-05-01",
  "usageTerms": "For use in digital marketing campaigns only.",
  "territory": "India",
  "allowsModification": false,
  "allowsResale": false,
  "allowsAttribution": true,
  "maxUsageCount": 100
}
```

---

## 📌 Microservice 4 — Notification Service (Port 8084)

### Entity: `Notification`

| Field | Type | Notes |
|---|---|---|
| `id` | Long | PK |
| `title` | String | Alert title |
| `message` | TEXT | Alert body |
| `recipientId` | Long | artistId or clientId |
| `recipientEmail` | String | For email delivery |
| `referenceId` | Long | licenseId, artworkId |
| `type` | Enum | `LICENSE_REQUEST`, `LICENSE_APPROVED`, `LICENSE_REJECTED`, `LICENSE_EXPIRED`, `LICENSE_RENEWAL`, `PURCHASE_CONFIRMED`, `NEW_MESSAGE`, `ARTWORK_UPLOADED`, `GENERAL` |
| `channel` | Enum | `IN_APP`, `EMAIL`, `SMS`, `PUSH` |
| `status` | Enum | `PENDING`, `SENT`, `FAILED`, `DELIVERED` |
| `isRead` | boolean | Read tracking |

### REST Endpoints

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/notifications` | Create notification |
| `GET` | `/api/notifications` | All notifications |
| `GET` | `/api/notifications/{id}` | By ID |
| `GET` | `/api/notifications/recipient/{id}` | All for recipient |
| `GET` | `/api/notifications/recipient/{id}/unread` | Unread only |
| `GET` | `/api/notifications/recipient/{id}/count` | Unread count |
| `GET` | `/api/notifications/type/{type}` | By type |
| `GET` | `/api/notifications/reference/{refId}` | By reference entity |
| `PATCH` | `/api/notifications/{id}/read` | Mark as read |
| `PATCH` | `/api/notifications/recipient/{id}/read-all` | Mark all as read |
| `POST` | `/api/notifications/alert/license-request` | Send license request alert |
| `POST` | `/api/notifications/alert/license-approved` | Send approval alert |
| `POST` | `/api/notifications/alert/license-expiry` | Send expiry reminder |
| `POST` | `/api/notifications/alert/purchase-confirmed` | Send purchase confirmation |
| `DELETE` | `/api/notifications/{id}` | Delete |

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.x running on port 3306

### Step 1 — Create Databases
```sql
-- Run setup-databases.sql in MySQL
source PixelVerse-Artist/setup-databases.sql;
```

### Step 2 — Start Each Service
```bash
# Terminal 1 — Artist Service (Port 8081)
cd PixelVerse-Artist && mvn spring-boot:run

# Terminal 2 — Portfolio Service (Port 8082)
cd PixelVerse-Portfolio && mvn spring-boot:run

# Terminal 3 — License Service (Port 8083)
cd PixelVerse-License && mvn spring-boot:run

# Terminal 4 — Notification Service (Port 8084)
cd PixelVerse-Notification && mvn spring-boot:run
```

### Step 3 — Test with curl / Postman
```bash
# Health check — Artist Service
curl http://localhost:8081/api/artists

# Health check — Portfolio Service
curl http://localhost:8082/api/artworks

# Health check — License Service
curl http://localhost:8083/api/licenses

# Health check — Notification Service
curl http://localhost:8084/api/notifications
```

---

## 🔗 JPA Relationships

```
Artist (8081)
   └── referenced by artistId field in:
         ├── Artwork.artistId         (Portfolio)
         ├── Collection.artistId      (Portfolio)
         ├── License.artistId         (License)
         ├── License.clientId         (License)
         └── Notification.recipientId (Notification)

Collection (8082)
   └── @OneToMany → Artwork (same service, FK: collection_id)

Artwork (8082)
   └── @ManyToOne → Collection
   └── referenced by License.artworkId (License Service)

License (8083)
   └── referenced by Notification.referenceId (Notification)
```

---

## 🛡️ Role-Based Access Control

| Role | Capabilities |
|---|---|
| `ARTIST` | Manage own profile, upload artworks, manage collections, approve/reject license requests |
| `CLIENT` | Browse artworks, request licenses, view own licenses and certificates |
| `ADMIN` | Full access — manage all artists, artworks, licenses, notifications |

---

## 🔮 Reflective Questions — Implementation Notes

### 1. Image Versioning / Watermarking
- Store each upload with a version suffix: `artwork-1-v2.png`
- Apply server-side watermark using **Java ImageIO** before storing preview URLs
- Store original in private S3 bucket; watermarked in public bucket

### 2. NFT Integration for Artwork Uniqueness
- Add `nftTokenId` and `blockchainNetwork` fields to `Artwork`
- Use **Web3j** to mint ERC-721 NFTs on Ethereum/Polygon on artwork upload
- Store transaction hash and smart contract address in DB

### 3. Customized Licensing Terms Per Client
- `License.usageTerms` field stores client-specific terms
- `territory`, `maxUsageCount`, `allowsModification`, `allowsResale` are all per-license
- Future: add `LicenseTemplate` entity for reusable term sets

### 4. Dashboard Analytics for Artists
- Track `viewCount`, `downloadCount` per Artwork
- License revenue aggregation: `SUM(price) WHERE artistId = ? AND status = ACTIVE`
- Popular artworks: top 5 by license purchase count
- Monthly earnings chart via date-grouped queries

---

## 📦 Future Enhancements

| Feature | Service | Notes |
|---|---|---|
| Spring Security + JWT | All | Role-based auth per endpoint |
| FeignClient integration | License ↔ Artist/Portfolio | Cross-service lookups |
| Kafka/RabbitMQ events | License → Notification | Async event publishing |
| PDF Certificate generation | License | iText / Apache PDFBox |
| AWS S3 file upload | Portfolio | Actual file storage |
| Eureka Service Discovery | All | Spring Cloud Netflix |
| API Gateway | All | Spring Cloud Gateway on port 8080 |
| Scheduled expiry checks | License / Notification | `@Scheduled` cron jobs |
