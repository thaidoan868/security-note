# Security Note

A Spring Boot project for learning Spring Security, focused on secure note management.

## Features
- User authentication and authorization (Spring Security)
- CRUD operations for notes (Create, Read, Update, Delete)
- Admin endpoint for listing all notes
- Layered architecture: Controller, DTO, Service, Mapper, Repository

## Tech Stack
- Java 21
- Spring Boot 3.5.4
- Maven
- RESTful API

## System Flow
```
Controller -> NoteDto -> NoteServiceImpl.method(NoteDto) -> NoteMapper.toEntity(NoteDto) -> Note -> NoteRepository.save(Note)
```

## Setup
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd security-note
   ```
2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```
3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Access the API:**
   - The application runs on `http://localhost:8080` by default.

## API Endpoints
### Notes
- `GET /api/notes` - List all notes
- `POST /api/notes` - Create a new note
- `GET /api/notes/{id}` - Get note by ID
- `PATCH /api/notes/{id}` - Update note by ID
- `DELETE /api/notes/{id}` - Delete note by ID

### Admin
- `GET /api/admin/notes` - List all notes (admin only)

## Contribution
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a pull request

## License
This project is for educational purposes.
