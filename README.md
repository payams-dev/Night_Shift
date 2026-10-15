# Night Shift at Hare's

**A camera-based survival-horror game prototype developed in Java with libGDX.**

*Night Shift at Hare's* is an independent survival-horror project exploring animatronic behavior, camera monitoring, power management, and night-based gameplay systems.

The project emphasizes object-oriented game architecture, state management, and the interaction between autonomous enemy behavior and limited player resources.

**Developer:** Michael Payams  
**Technology:** Java, libGDX, LWJGL3, Gradle  
**Status:** Playable development prototype; original visual assets and presentation are being developed.

<!-- Add your real screenshot here once captured. -->
<!-- ![Night Shift at Hare's main menu](docs/screenshots/main-menu.png) -->

## Overview

Players must survive a night shift while monitoring camera locations, managing limited power, and responding to animatronic threats.

The application is structured around gameplay systems that coordinate enemy behavior, player resources, location information, and night progression.

Originally developed as a private experimental project, *Night Shift at Hare's* has been reorganized into an independent portfolio prototype with new branding and replacement visual placeholders.

## Gameplay systems

### Animatronic behavior

The project uses Java classes to represent animatronic entities and their interactions with the game environment.

Enemy behavior is coordinated with the current night and gameplay state, providing a foundation for expanding movement patterns, difficulty, and encounters.

### Camera monitoring

The camera system represents different locations within the environment and allows gameplay to revolve around monitoring threats rather than directly controlling a character.

### Power management

A resource-management system tracks limited player power and supports gameplay decisions involving the use of defensive and monitoring equipment.

### Night progression

The game coordinates gameplay through night-specific logic and player state, providing a foundation for survival conditions, progression, and future balancing.

### Desktop presentation

The libGDX desktop application brings these systems together through a graphical main menu, visual assets, and interactive gameplay screens.

The current public-facing visuals include original placeholder graphics while dedicated artwork is developed.

## Engineering approach

The project was developed using object-oriented programming principles, with responsibilities separated across gameplay systems.

Key areas of interest include:

- **State management:** coordinating player conditions and game progression.
- **Entity modeling:** representing animatronic behavior through Java objects.
- **Resource constraints:** managing power as a limited gameplay resource.
- **Location modeling:** connecting enemy behavior with camera-accessible areas.
- **Separation of concerns:** organizing gameplay logic independently from desktop launching and presentation where practical.

The project is also an opportunity to practice refactoring an existing codebase, replacing prototype resources, and preparing software for public distribution.

## Technology

| Area | Technology |
|---|---|
| Language | Java |
| Game framework | libGDX |
| Desktop runtime | LWJGL3 |
| Build system | Gradle |
| Project structure | Multi-module Java application |
| Development tools | Eclipse, Git, GitHub |

## Building and running

### Requirements

- A compatible Java Development Kit (JDK)
- Internet access for downloading dependencies on the first build

The included Gradle wrapper handles the required Gradle version.

### Windows

```powershell
.\gradlew.bat :lwjgl3:run
```

### Linux / macOS

```bash
./gradlew :lwjgl3:run
```

### Compile the gameplay module

```bash
./gradlew :core:compileJava
```

### Run available tests

```bash
./gradlew :core:test
```

### Package the desktop application

```bash
./gradlew :lwjgl3:jar
```

The packaged JAR is generated under `lwjgl3/build/libs/`.

## Project structure

| Directory | Purpose |
|---|---|
| `core/` | Core Java gameplay systems and application logic |
| `lwjgl3/` | Desktop application launcher and packaging |
| `assets/` | Game textures, fonts, audio, and UI resources |
| `docs/` | Project documentation and screenshots, when added |
| `gradle/` | Gradle wrapper support files |

## Current development status

The project contains a functioning desktop application and foundational gameplay systems.

Work is ongoing in the following areas:

1. Original environmental artwork and animatronic presentation.
2. Gameplay balancing and refinement of enemy behavior.
3. Additional automated testing for gameplay logic.
4. Interface polish and improved presentation.
5. Further separation of core simulation rules from rendering where appropriate.

Some artwork is intentionally temporary. The repository is a development prototype, not a finished commercial game.

## Project origins and attribution

The project began as a private survival-horror experiment inspired by camera-based horror games and was subsequently rebranded as *Night Shift at Hare's*.

Its project structure was generated using [gdx-liftoff](https://github.com/libgdx/gdx-liftoff), and its desktop application uses [libGDX](https://libgdx.com/).

The public version is intended to use original or appropriately licensed distributable assets. Visual placeholders do not represent final artwork.

## Developer

**Michael Payams**  
North Carolina State University — Undergraduate pursuing Computer Science

[GitHub](https://github.com/payams-dev) · [LinkedIn](https://www.linkedin.com/in/michaelpayams/)
