# Architecture Migration Example

Android sample that documents a real architecture evolution:

**MVP → MVVM → MVI → Jetpack Compose + modern stack**

Built as a Pokédex (list + detail) against the public [PokéAPI](https://pokeapi.co/), with clean layering across **data → domain → presentation**.

---

## Why this repo exists

Most portfolio apps only show the final architecture. This one keeps the journey:

| Branch | Pattern | What it demonstrates |
|--------|---------|----------------------|
| [`mvp`](https://github.com/DwanZ/archMigrationExample/tree/mvp) | MVP + Clean | Contracts, presenters, explicit view callbacks |
| [`mvvm`](https://github.com/DwanZ/archMigrationExample/tree/mvvm) | MVVM + Clean | ViewModels replace presenters; LiveData/state ownership moves to VM |
| [`mvi`](https://github.com/DwanZ/archMigrationExample/tree/mvi) | MVI-style | Unidirectional flow: UI events → reducer-like VM → `StateFlow` |
| [`feature/compose-mvi`](https://github.com/DwanZ/archMigrationExample/tree/feature/compose-mvi) | Compose + MVI | Material3 UI, Navigation Compose, Coil, UiEffects |

Interview angle: *when* to migrate, *what* to keep, and how to ship incrementally without a big-bang rewrite.

---

## Current baseline (`feature/compose-mvi`)

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
- [ ] Hilt migration (optional; Koin kept with intentional Compose wiring)
- [ ] Screenshots / short GIF in this README

---

## App features

- Paginated Pokémon list
- Pokémon detail by name
- Loading / empty / error presentation states
- Navigation list → detail

---

## Architecture (high level)

```text
UI (Activity / Compose)
   ↓ events
ViewModel (state holder / MVI)
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

# historical architectures
git checkout mvp   # or mvvm / mvi

# active modernization
git checkout feature/compose-modernization
```

Open in Android Studio, sync Gradle, run the `app` configuration.

> Tip: compare the same screen across branches in an interview to walk through tradeoffs in 5–10 minutes.

---

## Tech decisions worth discussing

1. **Why MVI after MVVM?** Clearer event modeling, fewer ad-hoc LiveData channels, easier UI-state reasoning under concurrency.
2. **Why keep use cases?** Small app, but they document intent and stay testable when UI frameworks change.
3. **Migration strategy:** branch-per-architecture instead of deleting history — useful for teams mid-migration (Compose adoption, modularization).

---

## License

See [LICENSE](LICENSE).
