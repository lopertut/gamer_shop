#  GAMER SHOP – Mobile Store App

## Project Description
Gamer Shop is a mobile store designed for gamers. The goal of the project is to create a modern, responsive, and user-friendly shopping experience where users can browse gaming products, view details, and interact with a clean interface.

---

## Features
- Browse products through a clean and modern interface  
- View detailed information about each product  
- Simple navigation between pages  
- Responsive design for mobile devices  
- More features can be added in future development  

---

## Project Architecture
- Application is client-server
- Backend is MVC
- Frontend is something like MVVM

<img width="660" height="827" alt="image" src="https://github.com/user-attachments/assets/12655a4a-3331-470b-b698-8819d59259bd" />

---

## Tech Stack
- **Languages:** Kotlin, Go
- **Tools:** VS Code, Git, GitHub, Figma, Android studio
- **Database:** Supabase

---

## Backend Structure

```bash
backend/
├── handler/        # HTTP handlers and route controllers
├── middleware/     # Middleware functions (auth, logging, CORS, etc.)
├── model/          # Data models / structs
├── repository/     # Database access layer and queries
├── service/        # Business logic layer
├── .env.example    # Example environment variables
├── go.mod          # Go module dependencies
└── main.go         # Application entry point
```

## Folder Description

### `handler/`
Contains request handlers responsible for:
- Processing HTTP requests
- Validating input
- Returning API responses

### `middleware/`
Contains middleware used across the application, such as:
- Authentication & authorization
- Request logging
- Error handling
- CORS configuration

### `model/`
Defines application data structures and database models.

### `repository/`
Responsible for interacting with the database:
- SQL queries
- CRUD operations
- Data persistence

### `service/`
Implements the business logic of the application:
- Validation rules
- Core application functionality
- Communication between handlers and repositories

### `main.go`
The main entry point of the application:
- Initializes server
- Loads configuration
- Registers routes and middleware

### `.env.example`
Template file for required environment variables.

### `go.mod`
Manages Go dependencies and module configuration.

## Screens Description

### Home Page
Displays featured products.

### Product Details Page
Detailed information about a selected product.

### Registration Page
User registration form.

---

# Frontend Structure

```bash
main/
├── java/com/app/gamer_shop/
│   ├── api/             # API services and network requests
│   ├── models/          # Data models used in the application
│   ├── repositories/    # Data management and repository layer
│   ├── ui/              # Screens, components, and UI logic
│   ├── viewModels/      # ViewModels for managing UI state
│   ├── App.kt           # Application configuration class
│   └── MainActivity.kt  # Main activity and app entry point
├── res/                 # Resources (layouts, icons, strings, themes)
└── AndroidManifest.xml  # Android application configuration
```

# Folder Description

## `api/`
Contains classes responsible for:
- API requests
- Network communication
- Retrofit services or HTTP clients
- Handling backend responses

---

## `models/`
Contains application data models:
- Product models
- User models
- Order models
- Response objects

---

## `repositories/`
Responsible for managing data sources:
- Fetching data from APIs
- Local data caching
- Providing clean data access for ViewModels

---

## `ui/`
Contains all user interface related code:
- Screens
- Components
- Navigation
- UI state rendering

---

## `viewModels/`
Implements ViewModel logic using MVVM architecture:
- Manages screen state
- Handles business interaction between UI and repositories
- Provides reactive data for UI updates

---

## `App.kt`
Application-level configuration:
- Initializes dependencies
- Configures global application settings

---

## `MainActivity.kt`
Main entry point of the Android application:
- Hosts navigation
- Launches UI content
- Initializes the main application flow

---

## `res/`
Contains Android resources:
- Layouts
- Drawable icons
- Colors
- Fonts
- Strings
- Themes and styles

---

## `AndroidManifest.xml`
Defines Android application settings:
- Permissions
- Activities
- Application metadata
- App configuration

---

## Design Screenshots

### Home Page
<img width="758" height="1634" alt="image" src="https://github.com/user-attachments/assets/76191d16-f233-4cc4-b30d-3a3491623681" />


### Shop Page
(Shop screenshot here)

### Product Details
<img width="360" height="1680" alt="image" src="https://github.com/user-attachments/assets/65a684c3-636f-483d-a45b-12bf0314d2ef" />


### Registration Page
<img width="363" height="658" alt="image" src="https://github.com/user-attachments/assets/540fbcb3-3255-4d78-91f8-f2d24e3d2c06" />


---

## Development Model
The project follows a simple Kanban workflow.

- Flexible task management  
- Clear progress tracking  
- Easy to expand and update  
- Suitable for solo or small team development  

---

## Team Members
* **[Artur Matvejev](https://github.com/lopertut)** – Backend, frontend, disain, UI/UX
* **[Daniel Gvirdzhishvili](https://github.com/Ckoko228)** – Disain ja UI/UX
* **[Maksim Stsepelev](https://github.com/cursed-ken-kaneki)** – Disain ja UI/UX

---

## How to Run
1. Install go on your pc
2. start supabase database
3. Clone the repository
   ```bash
   git clone https://github.com/lopertut/gamer_shop
   ```
4. Open the project folder  
5. Open backend folder
6. Create and fill .env file. Backend folder have env example
7. Run backend server
   ```bash
   go run main.go
    ```

---

## Test Plan

* Test name: .env check
* Prerequisite: dont create .env file
* Step: run main script main.go
* Expected result: programm will throw error and will not run

---

# Persoonad ja kasutajalood

## Persoona 1 — Martin, 22

**Staatus:** üliõpilane  
**Huvid:** mängimine, tehnoloogia  

### Käitumine
- Kasutab igapäevaselt arvutit nii õppimiseks kui ka mängimiseks
- On aktiivne digiplatvormide ja veebiteenuste kasutaja

### Vajadused
- Otsib taskukohase hinnaga, kuid kvaliteetseid tarvikuid
- Eelistab hea hinna ja kvaliteedi suhtega tooteid

### Valikukriteeriumid
- Lähtub teiste kasutajate arvustustest
- Hindab kiiret ja mugavat ostuprotsessi

---

## Persoona 2 — Kadi, 34

**Staatus:** kontoritöötaja (turundusspetsialist)  
**Huvid:** töö efektiivsus, mugavad töövahendid  

### Käitumine
- Kasutab tehnoloogiat igapäevaselt töö tegemiseks
- Ei ole tehnikaekspert, eelistab lihtsaid ja arusaadavaid lahendusi

### Vajadused
- Vajab tööks usaldusväärseid ja mugavaid seadmeid
- Eelistab seadmeid, mis on kergesti kasutatavad ja ei vaja keerulist seadistamist

### Valikukriteeriumid
- Mugavus ja töökindlus on olulisemad kui hind
- Eelistab hästi hinnatud ja populaarseid tooteid

---

# Stsenaariumid

## Stsenaarium 1 — Martin

Martin soovib osta uut mängurihiirt. Ta avab rakenduse, sirvib hiirte kategooriat, filtreerib tooteid hinna ja hinnangute järgi ning loeb arvustusi. Ta leiab sobiva hiire, lisab selle ostukorvi ja vormistab tellimuse kiiresti mobiilimaksega.

---

## Stsenaarium 2 — Kadi

Kadi otsib töö jaoks mugavaid kõrvaklappe koos mikrofoniga. Ta avab rakenduse ja kasutab otsingut. Rakendus soovitab populaarseid ja hästi hinnatud tooteid. Kadi loeb lühikirjeldusi, valib ühe lihtsa ja usaldusväärse mudeli ning tellib selle koos kohaletoimetamisega kontorisse.

---

# Kasutajalood (User Stories)

- Kasutajana tahan ma sirvida tootekategooriaid (hiired, klaviatuurid, kõrvaklapid, mikrofonid), et leida kiiresti vajalik toode.

- Kasutajana tahan ma filtreerida ja sorteerida tooteid hinna ja hinnangute järgi, et leida parim valik vastavalt oma eelarvele.

- Kasutajana tahan ma lugeda teiste kasutajate arvustusi, et teha teadlik ostuotsus.

- Kasutajana tahan ma lisada toote ostukorvi ja maksta mugavalt mobiilis, et ostuprotsess oleks kiire ja lihtne.

- Kasutajana tahan ma saada soovitusi populaarsete toodete kohta, et leida kvaliteetseid ja usaldusväärseid seadmeid ilma pika otsimiseta.
