# Core

The core PaperSpigot plugin for MCHeroes' event.

Before using the Core's data/actions API, you will need to reference dependency to it.

- Make sure you add `MCHeroes` to your plugin.yml under the `depend` section.
- Add the Core as a dependency in your project.

## How to reference the CorePlugin singleton:

```java
CorePlugin core=CoreProvider.get();
// or use Bukkit's PluginManager
        Bukkit.getPluginManager().getPlugin("MCHeroes");
```

## How to use the actions API to get/set data:

```java
CorePlugin core=...

// Returns the current point value of the given player UUID.
        Integer points=core.getActionManager().run(new GetPointsAction(UUID));

// This returns an Integer for the old point value, but it can be ignored in most cases.
        core.getActionManager().run(new SetPointsAction(UUID,int));

// Can be null, returns the current team of the given player UUID.
        Team team=core.getActionManager().run(new GetTeamAction(UUID));
// or alternatively use the utils for simplifying action API calls
        TeamUtil.getTeam(core.getActionManager(),Player/UUID);
```

## How to register a minigame:

```java
// Make a class implementing Minigame
MinigameImpl minigame = ...
CorePlugin core = ...
        
core.getActionManager().run(new RegisterMinigameAction(minigame));
```

## How to get all minigame players:

```java
// Make a class implementing Minigame
CorePlugin core=...

        MinigamePlayerUtil.getPlayers(core.getActionManager());
```

## How to manage spectator status:

```java
// Make a class implementing Minigame
CorePlugin core=...

        core.getActionManager().run(new SetSpectatorAction(UUID));
        core.getActionManager().run(new UnsetSpectatorAction(UUID));
```