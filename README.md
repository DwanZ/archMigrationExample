# Architecture Migration Example

Android sample that documents a real architecture evolution:

**MVP → MVVM → MVI → Jetpack Compose + modern stack**

Built as a Pokédex (list + detail) against the public [PokéAPI](https://pokeapi.co/), with clean layering across **data → domain → presentation**.

---

## Demo

| Home | Detail (type-colored header) |
|------|------------------------------|
| ![Home](docs/screenshots/home.png) | ![Detail](docs/screenshots/detail.png) |

- Paginated list with pull-to-refresh
- Detail screen tints header/background from Pokémon types (same rule as the original XML: last mapped type wins)
- Compose Material 3 + Navigation + Coil

---

## Architecture branches

These branches are **intentional historical snapshots**. They use an older Android toolchain and are meant for **code reading / comparison**, not for building on modern Android Studio.

| Branch | Pattern | Look at these files first |
|--------|---------|---------------------------|
| [`mvp`](https://github.com/DwanZ/archMigrationExample/tree/mvp) | MVP + Clean | `HomeContract`, `HomePresenter`, `DetailPresenter` |
| [`mvvm`](https://github.com/DwanZ/archMigrationExample/tree/mvvm) | MVVM + Clean | `HomeViewModel` + `MutableLiveData`, `BaseViewModel` |
| [`mvi`](https://github.com/DwanZ/archMigrationExample/tree/mvi) | MVI-style | `HomeState`, `HomeEvent`, `HomeViewModel` (`StateFlow`) |
| [`main`](https://github.com/DwanZ/archMigrationExample/tree/main) | Compose + MVI | `MainActivity`, `ui/home`, `ui/detail`, `HomeEffect` |

What stays stable across migrations: **data / domain (use cases + repository)**. What changes: **presentation**.

Suggested walkthrough order: `mvp` → `mvvm` → `mvi` → `main`.

---

## Current baseline (`main`)

- Kotlin 1.9, Coroutines, `StateFlow`
- Clean architecture (repository, use cases, presentation)
- Koin 3 DI (+ Compose integration)
- Retrofit + OkHttp + Gson
- **Jetpack Compose (Material3)** list + detail
- Navigation Compose + one-shot **UiEffects** for navigation
- Coil for images
- AGP **8.7.3** / Gradle **8.9** / compileSdk **35**
- Unit tests (MockK + Turbine) + GitHub Actions CI

> **Android Studio tip:** set **Gradle JDK to 17** (`jbr-17`), not JDK 25. AGP 8.x requires JDK 17.

Legacy XML Activities remain in the repo for side-by-side comparison; the launcher is `MainActivity` (Compose).

## Modernization goals

- [x] Gradle + current AGP / Kotlin / compileSdk 35
- [x] Remove synthetics / `kotlin-android-extensions`
- [x] Jetpack Compose UI (list + detail)
- [x] Navigation Compose
- [x] Coil for images
- [x] MVI events + state + effects (navigation)
- [x] Unit tests for ViewModel (MockK + Turbine)
- [x] GitHub Actions: assemble + unit tests
- [x] Screenshots in this README
- [x] Document historical `mvp` / `mvvm` / `mvi` branches
- [ ] Hilt migration (optional; Koin kept with intentional Compose wiring)

---

## App features

- Paginated Pokémon list
- Pokémon detail by name
- Loading / empty / error presentation states
- Navigation list → detail
- Type-based header/background colors on detail

---

## Architecture (high level)

```text
UI (Compose)
   ↓ events
ViewModel (state + effects / MVI)
   ↓
Use cases
   ↓
Repository
   ↓
Remote data source (PokéAPI)
```

Domain stays independent of UI framework so MVP → MVVM → MVI → Compose can evolve without rewriting networking each time.

---

## Getting started

```bash
git clone https://github.com/DwanZ/archMigrationExample.git
cd archMigrationExample
git checkout main
```

Open in Android Studio, set Gradle JDK to **17**, sync Gradle, run the **`app`** configuration.

To inspect historical architectures:

```bash
git checkout mvp   # or mvvm / mvi
```

---

## Design notes

1. **MVI after MVVM:** clearer event modeling and a single UI state stream (`StateFlow`).
2. **Use cases kept small:** document intent and remain testable when the UI framework changes.
3. **UiEffects for navigation:** keep render state separate from one-shot side effects.
4. **Type colors on detail:** port of the original XML `setHeaderColor` rule (last mapped type wins), extended for contrast.
5. **Branch-per-architecture:** preserve migration history instead of deleting intermediate designs.

---

## Profile README

Ready-to-publish content: [`docs/github-profile-readme.md`](docs/github-profile-readme.md).

---

## License

See [LICENSE](LICENSE).
