# Core
The core PaperSpigot plugin for MCHeroes' event.

Before using the Core's data/actions API, you will need to reference dependency to it.
- Make sure you add `MCHeroes` to your plugin.yml under the `depend` section.
- Add the Core as a dependency in your project.

## How to reference the CorePlugin singleton:
```java
CorePlugin core = (CorePlugin) Bukkit.getPluginManager().getPlugin("MCHeroes");
```

## How to use the actions API to get/set data:
```java
CorePlugin core = ...

// Returns the current point value of the given player UUID.
Integer points = (Integer) core.getActionManager().run(new GetPointsAction(UUID));

// This returns an Integer for the old point value, but it can be ignored in most cases.
core.getActionManager().run(new SetPointsAction(UUID, int));

// Can be null, returns the current team of the given player UUID.
Team team = (Team) core.getActionManager().run(new GetTeamAction(UUID));
```
