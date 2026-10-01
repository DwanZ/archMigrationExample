# MVP snapshot

Historical branch of **archMigrationExample** using **MVP + Clean Architecture**.

This branch is a **code snapshot for comparison**. It uses an older Android toolchain and is not expected to sync/build on modern Android Studio.

## What this branch shows
- View / Presenter / Contract separation
- Presenters orchestrate use cases and push results to the View
- Same product surface: Pokémon list + detail (PokéAPI)

## Files to open first
- `view/home/ui/HomeContract.kt`
- `view/home/ui/HomePresenter.kt`
- `view/detail/ui/DetailContract.kt`
- `view/detail/ui/DetailPresenter.kt`
- `usecase/` and `data/` (shared domain/data layers)

## Navigation
- Previous: — (start of the migration story)
- Next: [`mvvm`](https://github.com/DwanZ/archMigrationExample/tree/mvvm)
- Current product (`main` / Compose): [`main`](https://github.com/DwanZ/archMigrationExample/tree/main)
