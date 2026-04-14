package combat;

import characters.Monster;
import characters.Player;

public class AIController {
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
