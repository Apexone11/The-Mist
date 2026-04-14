package combat;

import characters.Monster;
import characters.Player;
import exceptions.InvalidMenuChoiceException;
import inventory.Inventory;
import io.InputUtil;
import items.Item;
import items.Potion;

public class CombatEngine {
    private Player player;
    private Monster monster;
    private AIController ai;
    private boolean playerDefending;
    private boolean monsterDefending;

    public CombatEngine(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.ai = new AIController();
    }

    public void startCombat() {
        System.out.println("==============================");
        System.out.println("  ⚔ COMBAT: " + monster.getName());
        if (monster instanceof characters.Boss boss) {
            System.out.println("  \"" + boss.getBossQuote() + "\"");
        }
        System.out.println("==============================");

        while (!isCombatOver()) {
            playerDefending = false;
            monsterDefending = false;

            displayCombatStatus();
            playerTurn();

            if (isCombatOver()) break;

            monsterTurn();
        }

        if (player.getHp() <= 0) {
            System.out.println("You have been defeated...");
        } else {
            System.out.println("You defeated the " + monster.getName() + "!");
            System.out.println("Gained " + monster.getXpReward() + " XP!");
            player.addXp(monster.getXpReward());
        }
    }

    private void displayCombatStatus() {
        System.out.println("\nYour HP:    " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("Enemy HP:   " + monster.getHp() + "/" + monster.getMaxHp());
        System.out.println("\n1) Attack");
        System.out.println("2) Defend");
        System.out.println("3) Use Item");
        System.out.println("4) Run");
    }

    private void playerTurn() {
        boolean validChoice = false;
        while (!validChoice) {
            try {
                System.out.print("\nEnter choice: ");
                int choice = InputUtil.getIntInput();
                switch (choice) {
                    case 1:
                        playerAttack();
                        validChoice = true;
                        break;
                    case 2:
                        playerDefend();
                        validChoice = true;
                        break;
                    case 3:
                        useItem();
                        validChoice = true;
                        break;
                    case 4:
                        System.out.println("You ran away!");
                        monster.setHp(0); // Ends combat
                        validChoice = true;
                        break;
                    default:
                        throw new InvalidMenuChoiceException("Combat Menu", 1, 4, choice);
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void playerAttack() {
        int baseDamage = player.getStrength();
        
        // Check for weapon in inventory and apply its bonus
        // This is a simple implementation: it takes the highest attack bonus weapon in inventory
        int weaponBonus = 0;
        for (Item item : player.getInventory().getItems()) {
            if (item instanceof items.Weapon weapon) {
                if (weapon.getAttackBonus() > weaponBonus) {
                    weaponBonus = weapon.getAttackBonus();
                }
            }
        }
        
        int totalStrength = baseDamage + weaponBonus;
        int damage = totalStrength - (monsterDefending ? monster.getDefense() * 2 : monster.getDefense());
        
        if (damage < 1) damage = 1;
        monster.setHp(monster.getHp() - damage);
        
        String weaponMsg = weaponBonus > 0 ? " (incl. weapon bonus)" : "";
        System.out.println("You attacked the " + monster.getName() + " for " + damage + " damage!" + weaponMsg);
    }

    private void playerDefend() {
        playerDefending = true;
        System.out.println("You brace yourself for an attack!");
    }

    private void monsterTurn() {
        String action = ai.decideAction(monster, player);
        if (Action.ATTACK.equals(action)) {
            int damage = monster.getStrength() - (playerDefending ? player.getDefense() * 2 : player.getDefense());
            if (damage < 1) damage = 1;
            player.setHp(player.getHp() - damage);
            System.out.println("The " + monster.getName() + " attacked you for " + damage + " damage!");
        } else if (Action.DEFEND.equals(action)) {
            monsterDefending = true;
            System.out.println("The " + monster.getName() + " is defending!");
        }
    }

    private boolean isCombatOver() {
        return player.getHp() <= 0 || monster.getHp() <= 0;
    }

    private void useItem() {
        Inventory inventory = player.getInventory();
        inventory.displayItems();
        if (inventory.getItems().isEmpty()) {
            return;
        }

        try {
            System.out.print("\nSelect item to use or 0 to cancel: ");
            int choice = InputUtil.getIntInput();
            if (choice == 0) return;

            Item item = inventory.getItem(choice - 1);
            if (item instanceof Potion potion) {
                potion.use(player);
                inventory.removeItem(item);
            } else if (item != null) {
                System.out.println("You cannot use this item right now.");
            } else {
                throw new InvalidMenuChoiceException("Inventory Menu", 0, inventory.getItems().size(), choice);
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
