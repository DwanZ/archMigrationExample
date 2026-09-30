# Architecture Migration Example

Android sample that documents a real architecture evolution:

**MVP → MVVM → MVI → (in progress) Jetpack Compose + modern stack**

Built as a Pokédex (list + detail) against the public [PokéAPI](https://pokeapi.co/), with clean layering across **data → domain → presentation**.

> Target audience: **senior Android interviews** — show migration judgment, not just a greenfield demo.

---

## Why this repo exists

Most portfolio apps only show the final architecture. This one keeps the journey:

| Branch | Pattern | What it demonstrates |
|--------|---------|----------------------|
| [`mvp`](https://github.com/DwanZ/archMigrationExample/tree/mvp) | MVP + Clean | Contracts, presenters, explicit view callbacks |
| [`mvvm`](https://github.com/DwanZ/archMigrationExample/tree/mvvm) | MVVM + Clean | ViewModels replace presenters; LiveData/state ownership moves to VM |
| [`mvi`](https://github.com/DwanZ/archMigrationExample/tree/mvi) | MVI-style | Unidirectional flow: UI events → reducer-like VM → `StateFlow` |
| `feature/compose-modernization` | Compose + modern toolchain | Same product intent, updated platform stack (active work) |

Interview angle: *when* to migrate, *what* to keep, and how to ship incrementally without a big-bang rewrite.

---

## Current baseline (`mvi`)

- Kotlin, Coroutines, `StateFlow`
- Clean architecture (repository, use cases, presentation)
- Koin DI
- Retrofit + OkHttp + Gson
- XML Views (ViewBinding / DataBinding)
- Picasso

Known legacy (being upgraded on `feature/compose-modernization`):

- AGP 4.1 / Gradle 6.5 / Kotlin 1.4 / compileSdk 30
- `kotlin-android-extensions`, jcenter
- Placeholder unit/UI tests
- No CI

---

## Modernization goals (senior bar)

- [ ] Gradle Version Catalog + current AGP / Kotlin / compileSdk 35
- [ ] Jetpack Compose UI (list + detail) replacing XML screens
- [ ] Hilt (or keep Koin with clear rationale documented)
- [ ] Navigation Compose
- [ ] Coil for images
- [ ] Proper MVI: immutable state, sealed events/effects, tested reducers/VMs
- [ ] Unit tests for ViewModels / use cases (MockK or Turbine)
- [ ] GitHub Actions: assemble + unit tests
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
