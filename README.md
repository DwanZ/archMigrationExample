# MVVM snapshot

Historical branch of **archMigrationExample** using **MVVM + Clean Architecture**.

This branch is a **code snapshot for comparison**. It uses an older Android toolchain and is not expected to sync/build on modern Android Studio.

## What this branch shows
- Presenters replaced by `ViewModel`
- UI observes `LiveData` from the ViewModel
- Contracts/presenters removed from the presentation layer
- Same product surface: Pokémon list + detail (PokéAPI)

## Files to open first
- `view/home/ui/HomeViewModel.kt`
- `view/detail/ui/DetailViewModel.kt`
- `view/BaseViewModel.kt`
- `view/home/ui/HomeActivity.kt` (LiveData observers)
- `usecase/` and `data/`

## Navigation
- Previous: [`mvp`](https://github.com/DwanZ/archMigrationExample/tree/mvp)
- Next: [`mvi`](https://github.com/DwanZ/archMigrationExample/tree/mvi)
- Current product (`main` / Compose): [`main`](https://github.com/DwanZ/archMigrationExample/tree/main)
