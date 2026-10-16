# Night Shift at Hare's

**An independent, camera-based survival-horror prototype built in Java with libGDX.**

*Night Shift at Hare's* explores the systems behind a night-shift survival game: autonomous animatronic entities, monitored locations, limited power, player state, and the coordination of gameplay rules with a desktop interface. The project is an evolving, playable Java prototype with a working main menu and an original, simple endoskeleton render.

**Developer:** Michael Payams  
**Tech stack:** Java · libGDX · LWJGL3 · Gradle  
**Project type:** Independent game-development and software-engineering project  
**Status:** Active prototype development

> **Portfolio scope:** This is a work-in-progress engineering project, not a finished game. The repository demonstrates its current Java architecture, game-state components, desktop launcher, and presentation work. Some art, object names, and gameplay systems are provisional; their presence does not imply final functionality or balancing.

[Night Shift at Hare's — in-game main menu](screenshots/main-menu.png)


## Contents

- [Overview](#overview)
- [Current state and features](#current-state-and-features)
- [Architecture and engineering](#architecture-and-engineering)
- [Project layout](#project-layout)
- [Requirements and setup](#requirements-and-setup)
- [Build, run, and verify](#build-run-and-verify)
- [Screenshots](#screenshots)
- [Assets and attribution](#assets-and-attribution)
- [Development process and AI assistance](#development-process-and-ai-assistance)
- [Limitations and roadmap](#limitations-and-roadmap)
- [Independence disclaimer](#independence-disclaimer)
- [About the developer](#about-the-developer)

## Overview

The project's premise is simple: survive a night shift by paying attention to the environment, monitoring camera-accessible locations, and making decisions under resource constraints. The technical focus is on **how these interacting systems are modeled in Java**.

Rather than concentrating only on visual presentation, the codebase is organized around distinct concepts—animatronics, a night session, configuration, player state, power usage, and spatial locations—with libGDX screens providing the graphical application layer.

The project began as a private genre-inspired experiment. It has since been renamed and reorganized as *Night Shift at Hare's*, with original or distributable assets prepared for a public-facing portfolio version.

## Current state and features

The following distinctions are intentional: **confirmed running**, **represented in source**, and **planned/refining** do not mean the same thing.

| Area | Current state | Notes |
| --- | --- | --- |
| Desktop application | **Confirmed running** | The libGDX/LWJGL3 app launches on Windows using the included Gradle wrapper. |
| Main menu | **Confirmed rendering** | Includes the *Night Shift at Hare's* title presentation and a simple original endoskeleton visual. |
| Animatronic model | **Represented in source** | `Animatronic` and neutral enemy variants provide a foundation for entity behaviors. |
| Night/session coordination | **Represented in source** | `Night`, `NightConfig`, and `NightEngine` organize the night-based gameplay domain. |
| Player and power state | **Represented in source** | `PlayerState` and `PowerManager` separate player condition and resource concerns. |
| Camera/location model | **Represented in source** | `Camera`, `CameraLocationList`, and `Location` define spatial/camera concepts. |
| Gameplay and test screens | **Represented in source** | `GameScreen` and `DoorTestScreen` support presentation and development. |
| Full game balancing, final visuals, expanded encounters | **In development** | Not presented as completed features. |

### Gameplay concepts

**Camera-based observation.** Camera and location types provide the structure for a game in which the player must observe activity across multiple areas. The current asset directory includes provisional camera-location artwork and UI resources.

**Autonomous threats.** The Java domain includes a base animatronic type and multiple enemy-variant classes. Generic names such as `EnemyVariantA` are temporary engineering identifiers until original character designs and individual behaviors are finalized.

**Resource pressure.** A dedicated power manager and player-state class isolate resource and player concerns from other gameplay types. This separation supports future balancing and testing.

**Night-specific progression.** The night configuration and engine classes provide an organizing point for difficulty parameters, session progression, and interactions between enemy and player systems.

### Presentation

The game uses libGDX for the desktop interface. Current source includes a main menu, game screen, and door-test screen. The menu is already rendering with a simple original endoskeleton; final art direction and polished gameplay presentation remain ongoing work.

## Architecture and engineering

The repository follows a multi-module libGDX structure. The **`core`** module holds the game application and Java gameplay classes, while **`lwjgl3`** contains the desktop launcher and platform-specific packaging configuration.

### Core responsibilities

| Package / class | Responsibility in the design |
| --- | --- |
| `com.payamsdev.animatronics.Animatronic` | Base representation for animatronic entities. |
| `com.payamsdev.animatronics.EnemyVariantA`–`D` | Placeholder enemy implementations awaiting original identities. |
| `com.payamsdev.animatronics.TempAnimatronic` | Temporary/prototype entity used during development. |
| `com.payamsdev.game.Night` | A night/session and its constituent game entities. |
| `com.payamsdev.game.NightConfig` | Configuration values for a night/session. |
| `com.payamsdev.game.NightEngine` | Coordination point for night gameplay logic. |
| `com.payamsdev.game.PlayerState` | Player-related state. |
| `com.payamsdev.game.PowerManager` | Power/resource management. |
| `com.payamsdev.position.Camera` | Camera representation. |
| `com.payamsdev.position.CameraLocationList` | Camera/location collection. |
| `com.payamsdev.position.Location` | Spatial location representation. |
| `com.payamsdev.nightshift.Main` | libGDX application entry point. |
| `com.payamsdev.nightshift.MainMenuScreen` | Main-menu presentation and interaction. |
| `com.payamsdev.nightshift.GameScreen` | Gameplay presentation. |
| `com.payamsdev.nightshift.DoorTestScreen` | Development-oriented door screen. |
| `com.payamsdev.nightshift.lwjgl3.Lwjgl3Launcher` | Windows/Linux/macOS desktop launcher. |

*The table describes each component's architectural role based on the current project structure; it is not a claim that every gameplay interaction is complete or independently benchmarked.*

### Engineering objectives

This project is a practical exercise in:

- **Object-oriented decomposition:** separate enemy entities, configuration, session state, resource management, and presentation.
- **State-driven gameplay:** coordinate events and transitions across a night rather than placing every rule inside a rendering screen.
- **Maintainable prototypes:** replace provisional names and resources without unnecessarily rewriting the gameplay foundation.
- **Reproducible builds:** use a checked-in Gradle wrapper and clearly documented launch task.
- **Incremental verification:** compile, run, inspect the visual result, and investigate failures after refactors.

These are engineering goals and practices, not claims of exhaustive automated coverage.

## Project layout

> **Important:** The GitHub repository currently contains an additional directory layer. The Gradle project is inside `TempAnimatronic-parent/`, not at the repository root.

```text
Night_Shift/
├── README.md                         # Public-facing project overview
├── screenshots/                      # Add original screenshots here
└── TempAnimatronic-parent/           # Gradle project root
    ├── assets/
    │   ├── audio/                    # Ambience and UI sound
    │   ├── fonts/                    # Bitmap font and source font
    │   ├── textures/                 # Character, office, and camera textures
    │   └── ui/                       # libGDX UI skin and related assets
    ├── core/
    │   └── src/main/java/com/payamsdev/
    │       ├── animatronics/         # Enemy domain classes
    │       ├── game/                 # Night, configuration, player, power
    │       ├── nightshift/           # libGDX screens and application
    │       └── position/             # Camera and location model
    ├── lwjgl3/
    │   └── src/main/java/com/payamsdev/nightshift/lwjgl3/
    ├── gradle/wrapper/               # Gradle wrapper files
    ├── build.gradle
    ├── settings.gradle
    ├── gradlew
    └── gradlew.bat
```

Generated `build/` directories are build artifacts, not project source, and should be excluded from version control.

## Requirements and setup

- **JDK 21** (the development environment used for a successful local launch; the project currently emits Java 8 source/target deprecation warnings).
- Git, if cloning the repository.
- Windows, for the currently verified local launch. The LWJGL3 Gradle task is intended for desktop platforms, but other operating systems have not been verified here.
- Internet access for the initial Gradle dependency download, unless dependencies are already cached.

A separate Gradle installation is **not** required: the repository includes the Gradle wrapper.

### Clone

```bash
git clone <YOUR-REPOSITORY-URL>
cd Night_Shift/TempAnimatronic-parent
```

Replace `<YOUR-REPOSITORY-URL>` with the actual GitHub clone URL. If you already cloned the project, simply navigate into `TempAnimatronic-parent/`.

### Run on Windows (Git Bash)

```bash
cd ~/git/Night_Shift/TempAnimatronic-parent
./gradlew :lwjgl3:run
```

### Run on Windows (PowerShell)

```powershell
cd "$HOME\git\Night_Shift\TempAnimatronic-parent"
.\gradlew.bat :lwjgl3:run
```

The PowerShell path assumes the repository is under your home `git` directory; adjust it if you cloned elsewhere.

### Run on Linux / macOS

```bash
cd Night_Shift/TempAnimatronic-parent
./gradlew :lwjgl3:run
```

These commands use the `lwjgl3` module's configured run task. Compatibility beyond the tested Windows setup should be verified on the target machine.

## Build, run, and verify

From `TempAnimatronic-parent/`:

| Task | Command |
| --- | --- |
| Compile core Java | `./gradlew :core:compileJava` |
| Run desktop app | `./gradlew :lwjgl3:run` |
| Run available tests | `./gradlew :core:test` |
| Build desktop JAR | `./gradlew :lwjgl3:jar` |
| Clean generated output | `./gradlew clean` |

On Windows PowerShell, use `.\gradlew.bat` instead of `./gradlew`.

**Testing status:** The project has successfully compiled and launched locally. No automated test count is claimed because the supplied project structure does not establish a completed JUnit suite. A successful Gradle `test` task alone does not prove that tests exist or that gameplay is fully covered.

### Known setup notes

**Obsolete source/target warnings.** Running with JDK 21 may produce warnings about Java 8 compiler compatibility. These are warnings, not proof of a runtime failure.

**Bitmap-font texture errors.** A `.fnt` file references a PNG atlas using its `page` declaration. After renaming a font, verify that `assets/fonts/nightshift_font.fnt` refers to `nightshift_font.png`.

**Assets not found.** Launch using `:lwjgl3:run` from the Gradle root so the configured working directory is used. Running the launcher directly from an IDE may require manually setting its working directory to `assets/`.

**Generated files appearing in Git.** Remove tracked build output from the Git index and add it to `.gitignore`; do not delete your authored Java source or distributable assets.

## Screenshots

Real, in-engine screenshots are preferable to illustrative mockups for this portfolio. To capture the current menu on Windows:

1. Launch `./gradlew :lwjgl3:run`.
2. Navigate to the main menu and frame the game window.
3. Press **Win + Shift + S** and save the capture as `screenshots/main-menu.png` at the *repository root*.
4. Commit the image and uncomment the screenshot Markdown near the top of this README.

Optional later captures: gameplay/office screen, camera interface, and a side-by-side view of the original endoskeleton render and its in-game presentation.

Do not label development renders as final in-game screenshots.

## Assets and attribution

The public-facing project uses original or appropriately licensed/distributable assets. Current resource categories include:

- Original or replacement animatronic and environment textures.
- Menu ambience and UI audio.
- Bitmap-font files and their associated texture page.
- libGDX UI resources and starter-template assets.

**Redistribution note:** A filename or a renamed asset is not evidence of ownership or permission. Keep records of the source and license for third-party fonts, audio, and UI packs, including any attribution requirements. Only assets with appropriate distribution rights should appear in the public repository.

The underlying project structure was bootstrapped with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff) and runs on [libGDX](https://libgdx.com/). Third-party components retain their respective licenses.

## Development process and AI assistance

This project is developed and directed by Michael Payams. The work includes Java gameplay programming, scene and UI iteration, original asset preparation, build debugging, and repository maintenance.

Generative AI has been used as an **assistance tool** for development planning, suggestions for safe asset replacement, identifier-refactoring scripts, debugging explanations, and documentation. Suggested changes are reviewed against the local source and validated by rebuilding and running the application. AI assistance does not itself establish that code is correct or production-ready.

The repository is presented as an evolving prototype with transparent development practices, not as a claim that every line or asset was created without tools or assistance.

## Limitations and roadmap

### Current limitations

- The visual presentation and some naming are still provisional.
- Generic enemy classes are development placeholders, not a complete original character roster.
- Camera and office assets may use temporary names while final locations and designs are established.
- Gameplay balancing and complete end-to-end survival flow require additional validation.
- Automated regression coverage is not yet documented as complete.
- The nested Gradle project directory is a transitional repository layout.

### Planned improvements

1. Finish original character identities, behaviors, and visual assets.
2. Refine menu, camera, office, and door presentation.
3. Expand verification of game-state transitions, power consumption, and enemy behavior with automated tests.
4. Improve progression, balance, and player feedback across a complete night.
5. Simplify repository organization so the Gradle root can sit directly at the GitHub repository root.
6. Add polished in-engine screenshots and a short gameplay demonstration.

These are intentions, not features claimed to be finished.

## Independence disclaimer

*Night Shift at Hare's* is an **independent educational and portfolio project**. It is **not affiliated with, sponsored by, approved by, or endorsed by** *Five Nights at Freddy's*, Scott Cawthon, or any associated rights holder.

Some class names, variables, object instances, and graphics in the development branch are temporary placeholders for systems and character concepts that will receive original identities. They should not be interpreted as official characters, licensed material, or a representation of a third-party franchise.

The project takes broad inspiration from camera-based survival-horror gameplay. Its public release is intended to contain only independently authored or appropriately licensed content. This disclaimer does not replace the need to verify rights to source code and assets.

## About the developer

**Michael Payams** — North Carolina State University undergraduate pursuing Computer Science.

I am interested in Java, backend-oriented software engineering, gameplay systems, and building maintainable software through iterative development, debugging, and testing.

- **GitHub:** [payams-dev](https://github.com/payams-dev)
- **LinkedIn:** [michaelpayams](https://www.linkedin.com/in/michaelpayams/)

---

*Night Shift at Hare's is a work in progress. This README aims to distinguish what is implemented and verified from what is still under development.*
