# MVI snapshot

Historical branch of **archMigrationExample** using an **MVI-style** presentation layer on top of Clean Architecture.

This branch is a **code snapshot for comparison**. It uses an older Android toolchain and is not expected to sync/build on modern Android Studio.

## What this branch shows
- Unidirectional flow: UI events → ViewModel → single UI state
- `HomeEvent` + `HomeState` + `StateFlow`
- ViewModel reduces API results into explicit states (`Loading`, `Success`, `Error`, …)
- Same product surface: Pokémon list + detail (PokéAPI)

## Files to open first
- `view/home/HomeEvent.kt`
- `view/home/HomeState.kt`
- `view/home/ui/HomeViewModel.kt`
- `view/detail/ui/DetailViewState.kt`
- `view/detail/ui/DetailViewModel.kt`

## Navigation
- Previous: [`mvvm`](https://github.com/DwanZ/archMigrationExample/tree/mvvm)
- Next: [`main`](https://github.com/DwanZ/archMigrationExample/tree/main) (Compose + MVI + modern toolchain)
