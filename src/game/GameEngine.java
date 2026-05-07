/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package game;

import characters.Dialogue;
import characters.Monster;
import characters.NPC;
import characters.Player;
import combat.CombatEngine;
import exceptions.InvalidMenuChoiceException;
import inventory.Inventory;
import io.InputUtil;
import items.Item;
import items.Potion;
import util.ConsoleUI;
import world.Room;
import world.WorldMap;
import util.RandomUtil;
import util.WeightedBag;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The core engine of the game. Manages the main game loop, movement, combat, and interactions.
 */
public class GameEngine {

    private GameState gameState;

    /**
     * Initializes a new GameEngine with an empty GameState.
     */
    public GameEngine() {
        this.gameState = new GameState();
    }

    /**
     * Starts a new game by loading the map, initiating player creation, and entering the game loop.
     * @throws InvalidMenuChoiceException if an invalid choice is made during player creation
     */
    public void startNewGame() throws InvalidMenuChoiceException {
        WorldMap worldMap = new WorldMap();
        try {
            worldMap.loadMap("data/rooms.json");
            gameState.setWorldMap(worldMap);
            gameState.setCurrentRoom(worldMap.getRoom(worldMap.getStartRoomId()));
            ConsoleUI.printBox("The Mist gathers as you step into the unknown...");
        } catch (FileNotFoundException e) {
            System.out.println("Error: rooms.json not found!");
            return;
        }

        GameState.playerCreation(gameState);
        
        displayRoom(gameState.getCurrentRoom());
        runGameLoop();
    }

    /**
     * Loads an existing game from a save file.
     */
    public void loadExistingGame() {
        WorldMap worldMap = new WorldMap();
        try {
            worldMap.loadMap("data/rooms.json");
            GameState loadedState = io.SaveManager.loadGame(worldMap);
            if (loadedState == null || loadedState.getPlayer() == null) {
                ConsoleUI.printBox("Error: No valid save game found.");
                return;
            }
            this.gameState = loadedState;
            ConsoleUI.printBox("The mists part as you return to Oakhaven...");
            displayRoom(gameState.getCurrentRoom());
            runGameLoop();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * The main loop of the game where the player can choose various actions like exploring, talking, or checking their journal.
     */
    public void runGameLoop() {
        boolean inGame = true;
        int choice;

        while (inGame) {
            ConsoleUI.printHeader("Main Menu");
            System.out.println("""
                    [1]  Explore / Move
                    [2]  Talk to Residents
                    [3]  Journal / Quests
                    [4]  Character Sheet
                    [5]  Inventory / Equipment
                    [6]  Save Progress
                    [7]  Exit to Main Menu
                    """);
            ConsoleUI.printDivider();

            try {
                System.out.print("Select action: ");
                choice = InputUtil.getIntInput();
                switch (choice) {
                    case (1): {
                        handleMovement();
                        break;
                    }
                    case (2): {
                        handleDialogue();
                        break;
                    }
                    case (3): {
                        displayJournal();
                        break;
                    }
                    case (4): {
                        displayCharacterSheet();
                        break;
                    }
                    case (5): {
                        useItem();
                        break;
                    }
                    case (6): {
                        io.SaveManager.saveGame(gameState);
                        break;
                    }
                    case (7): {
                        inGame = false;
                        break;
                    }
                    default: {
                        throw new InvalidMenuChoiceException("Action Menu", 1, 7, choice);
                    }
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the quest journal, showing active and completed quests.
     */
    private void displayJournal() {
        characters.QuestLog log = gameState.getPlayer().getQuestLog();
        ConsoleUI.printHeader("Journal");
        System.out.println("  ACTIVE QUESTS:");
        if (log.getActiveQuests().isEmpty()) System.out.println("  - None");
        for (String q : log.getActiveQuests()) System.out.println("  - " + q);
        
        System.out.println("\n  COMPLETED QUESTS:");
        if (log.getCompletedQuests().isEmpty()) System.out.println("  - None");
        for (String q : log.getCompletedQuests()) System.out.println("  - " + q);
        
        ConsoleUI.printDivider();
        System.out.println("\n  [Press Enter to return]");
        InputUtil.waitForEnter();
    }

    /**
     * Handles player movement between rooms based on user input.
     */
    private void handleMovement() {
        System.out.println("Move where? (N/S/E/W/U/D): ");
        String direction = InputUtil.getStringInput().trim().toUpperCase();

        Room currentRoom = gameState.getCurrentRoom();
        if (currentRoom.hasNeighbor(direction)) {
            String nextRoomId = currentRoom.getNeighborId(direction);
            Room nextRoom = gameState.getWorldMap().getRoom(nextRoomId);
            gameState.setCurrentRoom(nextRoom);
            ConsoleUI.printBox("You traveled " + direction);
            displayRoom(nextRoom);
            checkEncounter(nextRoom);
            checkLoot();
        } else {
            System.out.println("\n[!] You cannot move that way.");
        }
    }

    /**
     * Handles the selection of an NPC to talk to in the current room.
     */
    private void handleDialogue() {
        Room currentRoom = gameState.getCurrentRoom();
        List<NPC> npcs = currentRoom.getNpcs();
        if (npcs.isEmpty()) {
            System.out.println("No one here but the Mist.");
            return;
        }

        System.out.println("\nWho do you want to talk to?");
        for (int i = 0; i < npcs.size(); i++) {
            System.out.println((i + 1) + ") " + npcs.get(i).getName());
        }
        System.out.println("0) Back");

        try {
            int choice = InputUtil.getIntInput();
            if (choice == 0) return;
            if (choice > 0 && choice <= npcs.size()) {
                startDialogue(npcs.get(choice - 1));
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts a branching dialogue with the specified NPC.
     * @param npc the NPC to interact with
     */
    private void startDialogue(NPC npc) {
        Dialogue currentDialogue = npc.getDialogue(npc.getStartDialogueId());
        while (currentDialogue != null) {
            ConsoleUI.printHeader(npc.getName());
            ConsoleUI.printBox(currentDialogue.getText());

            List<Dialogue.Choice> choices = currentDialogue.getChoices();
            if (choices.isEmpty()) {
                System.out.println("\n[Press Enter to end conversation]");
                InputUtil.waitForEnter();
                break;
            }

            for (int i = 0; i < choices.size(); i++) {
                System.out.println((i + 1) + ") " + choices.get(i).getText());
            }

            try {
                int choiceIdx = InputUtil.getIntInput();
                if (choiceIdx > 0 && choiceIdx <= choices.size()) {
                    Dialogue.Choice selectedChoice = choices.get(choiceIdx - 1);
                    applyEffect(selectedChoice.getEffect());
                    currentDialogue = npc.getDialogue(selectedChoice.getNextDialogueId());
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Applies a narrative effect from a dialogue choice, such as increasing a stat or starting a quest.
     * @param effect the effect string to parse and apply
     */
    private void applyEffect(String effect) {
        if (effect == null) return;
        Player player = gameState.getPlayer();
        if (effect.startsWith("ADD_STRENGTH:")) {
            int val = Integer.parseInt(effect.split(":")[1]);
            player.setStrength(player.getStrength() + val);
            ConsoleUI.printBox("You feel a surge of power! Strength +" + val);
        } else if (effect.startsWith("ADD_MAGIC:")) {
            int val = Integer.parseInt(effect.split(":")[1]);
            player.setMagic(player.getMagic() + val);
            player.learnSkill(new characters.Skill("Mist Blast", "A blast of pure condensed mist.", 15, 0, "DAMAGE"));
            ConsoleUI.printBox("Your inner spark glows brighter! Magic enhanced. Learned 'Mist Blast'!");
        } else if (effect.startsWith("QUEST_ADD:")) {
            String quest = effect.split(":")[1];
            player.getQuestLog().addQuest(quest);
            ConsoleUI.printBox("New Quest: " + quest);
        } else if (effect.startsWith("LEARN_SKILL:")) {
            String[] parts = effect.split(":");
            player.learnSkill(new characters.Skill(parts[1], parts[2], Integer.parseInt(parts[3]), 0, parts[4]));
            ConsoleUI.printBox("Learned Skill: " + parts[1]);
        }
    }

    /**
     * Displays information about the specified room.
     * @param room the room to display
     */
    private void displayRoom(Room room) {
        ConsoleUI.printHeader(room.getName());
        ConsoleUI.printBox(room.getDescription());
        
        System.out.println("  Exits: " + String.join(", ", room.getNeighbors().keySet()));
        if (!room.getNpcs().isEmpty()) {
            System.out.print("  Inhabitants: ");
            for (NPC npc : room.getNpcs()) {
                System.out.print(npc.getName() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks for a random monster encounter in the specified room.
     * @param room the room to check for encounters
     */
    private void checkEncounter(Room room) {
        double roll = RandomUtil.randomDouble();

        if (roll < room.getEncounterRate()) {
            Monster monster = spawnMonster();
            System.out.println("\n⚠ A " + monster.getName() + " appears!");
            CombatEngine combat = new CombatEngine(gameState.getPlayer(), monster);
            combat.startCombat();
        }
    }

    /**
     * Spawns a random monster based on the player's current room/progression.
     * @return a new Monster instance
     */
    private Monster spawnMonster() {
        String roomId = gameState.getCurrentRoom().getId();
        int roomNum = Integer.parseInt(roomId.substring(1));

        WeightedBag<Monster> bag = new WeightedBag<>();

        if (roomNum <= 10) {
            // Early Game Monsters (R1-R10)
            bag.add(new Monster("Mist Midge", 15, 5, 2, 10), 40);
            bag.add(new Monster("Corrupted Rat", 20, 7, 3, 15), 30);
            bag.add(new Monster("Foggy Slime", 25, 6, 4, 20), 20);
            bag.add(new Monster("Lost Wanderer", 35, 10, 5, 30), 10);
            bag.add(new Monster("Mist Bat", 12, 4, 1, 8), 25);
            bag.add(new Monster("Blighted Toad", 22, 6, 3, 18), 20);
            bag.add(new Monster("Shadow Crawler", 30, 9, 4, 25), 15);
            bag.add(new Monster("Cursed Owl", 18, 12, 2, 22), 15);
            bag.add(new Monster("Grave Beetle", 40, 5, 8, 20), 10);
            bag.add(new Monster("Mist Serpent", 28, 14, 3, 35), 5);
        } else if (roomNum <= 20) {
            // Mid Game Monsters (R11-R20)
            bag.add(new Monster("Skeletal Guard", 50, 15, 10, 50), 40);
            bag.add(new Monster("Mist Ghoul", 60, 18, 8, 60), 30);
            bag.add(new Monster("Gargoyle", 80, 20, 15, 80), 20);
            bag.add(new Monster("Cursed Specter", 70, 25, 5, 100), 10);
            bag.add(new Monster("Zombie Soldier", 55, 14, 12, 55), 35);
            bag.add(new Monster("Shadow Stalker", 45, 22, 6, 65), 25);
            bag.add(new Monster("Blighted Ent", 100, 12, 18, 90), 15);
            bag.add(new Monster("Mist Witch", 40, 30, 4, 110), 10);
            bag.add(new Monster("Crypt Spider", 65, 19, 9, 75), 20);
            bag.add(new Monster("Ghostly Knight", 90, 25, 20, 150), 5);
        } else if (roomNum < 29) {
            // Late Game Monsters (R21-R28)
            bag.add(new Monster("Nightmare Stalker", 100, 30, 20, 150), 40);
            bag.add(new Monster("Ancient Guardian", 150, 35, 30, 200), 30);
            bag.add(new Monster("Void Creeper", 120, 45, 15, 250), 20);
            bag.add(new Monster("Abyssal Horror", 200, 50, 40, 500), 10);
            bag.add(new Monster("Mist Dragonling", 180, 55, 35, 600), 8);
            bag.add(new Monster("Shadow Revenant", 140, 60, 25, 450), 15);
            bag.add(new Monster("Void Sentinel", 250, 40, 50, 700), 5);
            bag.add(new Monster("Blighted Behemoth", 300, 70, 60, 1000), 2);
            bag.add(new Monster("Mist Weaver", 160, 65, 30, 550), 12);
            bag.add(new Monster("Soulless Husk", 110, 80, 10, 400), 10);
            bag.add(new Monster("Echo of Chaos", 190, 75, 45, 800), 5);
        } else if (roomNum == 29) {
            // Final Boss
            return new characters.Boss("The Mist Lord", 1000, 100, 80, 10000, "The Mist is all there was, and all there shall ever be.");
        }

        Monster selected = bag.getRandom();
        // Fallback
        if (selected == null) return new Monster("Mist Midge", 15, 5, 2, 10);
        
        // Return a fresh instance
        return new Monster(selected.getName(), selected.getHp(), selected.getStrength(), selected.getDefense(), selected.getXpReward());
    }

    /**
     * Checks if the player finds random loot in the current room.
     */
    private void checkLoot() {
        if (util.RandomUtil.randomDouble() < 0.3) {
            Item item = getRandomItem();
            System.out.println("\n🎁 You found something in the Mist: " + item.getName() + "!");
            gameState.getPlayer().getInventory().addItem(item);
        }
    }

    /**
     * Generates a random item from a weighted bag.
     * @return a random Item
     */
    private Item getRandomItem() {
        WeightedBag<Item> bag = new WeightedBag<>();
        
        // Potions (10 types)
        bag.add(new Potion("Weak Health Potion", "Restores 20 HP", 20), 50);
        bag.add(new Potion("Health Potion", "Restores 40 HP", 40), 40);
        bag.add(new Potion("Strong Health Potion", "Restores 70 HP", 70), 30);
        bag.add(new Potion("Greater Health Potion", "Restores 100 HP", 100), 20);
        bag.add(new Potion("Super Health Potion", "Restores 150 HP", 150), 10);
        bag.add(new Potion("Full Restore Potion", "Restores 500 HP", 500), 5);
        bag.add(new Potion("Antidote", "Restores 15 HP and clears toxins", 15), 30);
        bag.add(new Potion("Elixir", "Restores 50 HP", 50), 25);
        bag.add(new Potion("Holy Water", "Restores 80 HP", 80), 15);
        bag.add(new Potion("Mist Essence", "Restores 120 HP", 120), 10);

        // Weapons (20 types)
        bag.add(new items.Weapon("Rusty Dagger", "A dull blade.", 5), 50);
        bag.add(new items.Weapon("Iron Sword", "A standard soldier's sword.", 10), 40);
        bag.add(new items.Weapon("Steel Longsword", "A fine blade.", 15), 30);
        bag.add(new items.Weapon("War Hammer", "Heavy and crushing.", 20), 25);
        bag.add(new items.Weapon("Morning Star", "Spiked and deadly.", 22), 20);
        bag.add(new items.Weapon("Battle Axe", "Good for splitting wood and heads.", 25), 15);
        bag.add(new items.Weapon("Silver Rapier", "Effective against specters.", 18), 20);
        bag.add(new items.Weapon("Greatsword", "Requires two hands and great strength.", 35), 10);
        bag.add(new items.Weapon("Katana", "A sharp, curved blade.", 28), 12);
        bag.add(new items.Weapon("Mace", "Simple but effective.", 12), 35);
        bag.add(new items.Weapon("Flail", "Hard to master, hard to block.", 24), 18);
        bag.add(new items.Weapon("Spear", "Keeps enemies at a distance.", 14), 30);
        bag.add(new items.Weapon("Halberd", "Versatile reach weapon.", 26), 15);
        bag.add(new items.Weapon("Club", "Better than nothing.", 4), 60);
        bag.add(new items.Weapon("Quarterstaff", "Don't underestimate a big stick.", 8), 45);
        bag.add(new items.Weapon("Excalibur", "Legendary sword of kings.", 100), 1);
        bag.add(new items.Weapon("Void Blade", "Absorbs light and life.", 50), 3);
        bag.add(new items.Weapon("Mist Reaver", "Specifically designed for these lands.", 45), 5);
        bag.add(new items.Weapon("Dragon Slayer", "Heavy enough to crush scales.", 60), 2);
        bag.add(new items.Weapon("Shadow Fang", "A blade that drips darkness.", 30), 10);

        return bag.getRandom();
    }

    /**
     * Displays the player's character sheet, including stats, level, and XP.
     */
    private void displayCharacterSheet() {
        Player p = gameState.getPlayer();
        ConsoleUI.printHeader("Character Sheet");
        System.out.printf("  NAME: %-20s  LEVEL: %d\n", p.getName(), p.getLevel());
        System.out.printf("  HP:   %d/%-18d  XP:    %d/%d\n", p.getHp(), p.getMaxHp(), p.getXp(), p.getLevel() * 100);
        ConsoleUI.printDivider();
        System.out.println("  STATS:");
        System.out.printf("  Strength: %-10d  Defense: %-10d\n", p.getStrength(), p.getDefense());
        System.out.printf("  Magic:    %-10d  Speed:   %-10d\n", p.getMagic(), p.getSpeed());
        ConsoleUI.printDivider();
        System.out.println("\n  [Press Enter to return]");
        InputUtil.waitForEnter();
    }

    /**
     * Opens the inventory and allows the player to use an item.
     */
    private void useItem() {
        Inventory inventory = gameState.getPlayer().getInventory();
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
                potion.use(gameState.getPlayer());
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
