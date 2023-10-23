package mcheroes.core.minigames.actions;

import mcheroes.core.api.action.Action;
import mcheroes.core.api.minigame.Minigame;

import java.util.Set;

public record GetAllMinigamesAction() implements Action<Set<Minigame>> {

}
