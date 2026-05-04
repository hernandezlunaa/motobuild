# MotoBuild

MotoBuild is a motorcycle build planning web app. The app lets users create motorcycle builds, browse motorcycles, add parts to builds, track planned vs installed parts, view total cost, and see warnings for incompatible parts, required/recommended parts, and conflicting parts.

The project uses a Spring Boot backend with Thymeleaf pages and a MySQL database.

## Features

- User registration and login
- User-specific motorcycle builds
- Motorcycle catalog with bike images
- Parts catalog with search, filtering, sorting, and build selection
- Add and remove parts from builds
- Planned and installed part status
- Total build cost and cart cost
- Compatibility and incompatibility warnings
- Part dependency warnings
- Part conflict warnings
- Build rename, bike change, delete, and undo delete
- Account page with profile info, stats, and recent activity
- SQL triggers for account activity tracking
- Light and dark mode

## Tech Stack

- Java
- Spring Boot
- Thymeleaf
- Spring Data JPA
- MySQL
- Bootstrap
- HTML/CSS/JavaScript

## Database Setup

The SQL files are in the `database` directory.

Run the SQL files in this order:

```sql
database/schema.sql
database/seed.sql
```

The schema file creates the database, tables, indexes, relationships, and triggers.

The seed file adds the main motorcycles, parts, categories, compatibility rules, dependency rules, conflict rules, and starter data.

There is also an optional test file:

```sql
database/test.sql
```

Run this only if you want the default test user and test builds. The test file creates a user with multiple builds for testing normal builds, empty builds, full builds, and incompatible part warnings.

## Default Test User

If you run `database/test.sql`, you can log in with:

```text
Email: test@moto
Password: test123
```

## How to Run the App

1. Clone the repository.

```bash
git clone <your-repo-url>
```

2. Open the project in IntelliJ IDEA.

3. Make sure MySQL is running.

4. Run the SQL files in MySQL Workbench:

```text
1. database/schema.sql
2. database/seed.sql
3. database/test.sql   optional, but needed for the test user
```

5. Update `src/main/resources/application.properties` with your MySQL username and password.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/motobuild?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password_here
```

6. Run the Spring Boot MotobuildApplication.

7. Open the app in your browser:

```text
http://localhost:8080/login
```

8. Log in with the test user if you ran `test.sql`:

```text
Email: test@moto
Password: test123
```

## Main Database Tables

The main tables used in the project are:

- `users` stores account information.
- `motorcycles` stores available motorcycles.
- `part_categories` stores part category names.
- `parts` stores the parts catalog.
- `builds` stores user-created builds.
- `build_parts` connects builds and parts.
- `bike_part_incompatibility` stores parts that do not fit certain bikes.
- `part_dependencies` stores parts that require or recommend other parts.
- `part_conflicts` stores parts that should not be used together.
- `account_activity` stores recent user activity.

## Activity Log

The account page shows recent activity for the logged-in user. Some activity is recorded using SQL triggers, such as:

- Account created
- Build created
- Build deleted
- Part added
- Part removed

Other activity, such as profile updates or password changes, is logged through the Java service layer.

## Notes

This project uses manual SQL setup. The database should be created by running the SQL files before starting the app.

The test user is optional, but it is useful for quickly checking the main features of the project.
