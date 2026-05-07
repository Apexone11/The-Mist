package combat;

import characters.Monster;
import characters.Player;
import exceptions.InvalidMenuChoiceException;
import inventory.Inventory;
import io.InputUtil;
import items.Item;
import items.Potion;

import util.ConsoleUI;

/**
 * Manages the turn-based combat system between the player and monsters.
 */
public class CombatEngine {
    private Player player;
    private Monster monster;
    private AIController ai;
    private boolean playerDefending;
    private boolean monsterDefending;

    /**
     * Constructs a new CombatEngine.
     * @param player the player involved in combat
     * @param monster the monster being fought
     */
    public CombatEngine(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.ai = new AIController();
    }

    /**
     * Starts the combat loop and handles the win/loss conditions.
     */
    public void startCombat() {
        ConsoleUI.printHeader("Combat: " + monster.getName());
        if (monster instanceof characters.Boss boss) {
            ConsoleUI.printBox("\"" + boss.getBossQuote() + "\"");
        }

        while (!isCombatOver()) {
            playerDefending = false;
            monsterDefending = false;

            displayCombatStatus();
            playerTurn();

            if (isCombatOver()) break;

            monsterTurn();
        }

        if (player.getHp() <= 0) {
            ConsoleUI.printBox("You have been defeated...");
        } else {
            ConsoleUI.printBox("Victory! You defeated the " + monster.getName() + "!");
            System.out.println("Gained " + monster.getXpReward() + " XP!");
            player.addXp(monster.getXpReward());
        }
    }

    /**
     * Displays the current health of the player and monster, along with combat options.
     */
    private void displayCombatStatus() {
        ConsoleUI.printDivider();
        System.out.println("  YOU: " + player.getHp() + "/" + player.getMaxHp() + " HP");
        System.out.println("  FOE: " + monster.getHp() + "/" + monster.getMaxHp() + " HP");
        ConsoleUI.printDivider();
        System.out.println("""
                1) Attack
                2) Special Skills
                3) Use Item
                4) Defend
                5) Try to Escape
                """);
    }

    /**
     * Processes the player's turn, allowing them to choose an action.
     */
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
                        validChoice = useSkill();
                        break;
                    case 3:
                        useItem();
                        validChoice = true;
                        break;
                    case 4:
                        playerDefend();
                        validChoice = true;
                        break;
                    case 5:
                        System.out.println("You ran away!");
                        monster.setHp(0); // Ends combat
                        validChoice = true;
                        break;
                    default:
                        throw new InvalidMenuChoiceException("Combat Menu", 1, 5, choice);
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays available skills and allows the player to select one to use.
     * @return true if a skill was used, false if canceled or no skills available
     */
    private boolean useSkill() {
        java.util.List<characters.Skill> skills = player.getSkills();
        if (skills.isEmpty()) {
            System.out.println("You have no special skills yet.");
            return false;
        }

        System.out.println("\n--- SPECIAL SKILLS ---");
        for (int i = 0; i < skills.size(); i++) {
            characters.Skill s = skills.get(i);
            System.out.println((i + 1) + ") " + s.getName() + " (" + s.getDescription() + ")");
        }
        System.out.println("0) Back");

        try {
            int choice = InputUtil.getIntInput();
            if (choice == 0) return false;
            if (choice > 0 && choice <= skills.size()) {
                characters.Skill selected = skills.get(choice - 1);
                executeSkill(selected);
                return true;
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Executes the effect of a chosen skill.
     * @param skill the skill to execute
     */
    private void executeSkill(characters.Skill skill) {
        ConsoleUI.printBox("You used " + skill.getName() + "!");
        if ("DAMAGE".equals(skill.getType())) {
            int damage = skill.getPower() + player.getMagic();
            monster.setHp(monster.getHp() - damage);
            System.out.println("It dealt " + damage + " magic damage!");
        } else if ("HEAL".equals(skill.getType())) {
            int heal = skill.getPower() + player.getMagic();
            player.setHp(Math.min(player.getMaxHp(), player.getHp() + heal));
            System.out.println("You restored " + heal + " HP!");
        }
    }

    /**
     * Calculates and applies damage from the player's attack to the monster.
     */
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

    /**
     * Sets the player to a defending state, reducing incoming damage for one turn.
     */
    private void playerDefend() {
        playerDefending = true;
        System.out.println("You brace yourself for an attack!");
    }

    /**
     * Executes the monster's turn using the AI controller.
     */
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

    /**
     * Checks if either the player or the monster has been defeated.
     * @return true if combat is over, false otherwise
     */
    private boolean isCombatOver() {
        return player.getHp() <= 0 || monster.getHp() <= 0;
    }

    /**
     * Allows the player to use a potion from their inventory during combat.
     */
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
