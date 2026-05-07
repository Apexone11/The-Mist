package combat;

import characters.Monster;
import characters.Player;

/**
 * Controller for monster AI behavior during combat.
 */
public class AIController {
    /**
     * Decides the next action for a monster based on its state and the player's state.
     * @param monster the monster taking action
     * @param player the player being targeted
     * @return the chosen action as a string (from Action class)
     */
    public String decideAction(Monster monster, Player player) {
        if (monster.getHp() < monster.getMaxHp() * 0.3) {
            return Action.DEFEND;
        }
        if (player.getHp() < player.getMaxHp() * 0.3) {
            return Action.ATTACK;
        }
        return Action.ATTACK;
    }
}
