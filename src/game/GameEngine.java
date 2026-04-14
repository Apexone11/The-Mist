/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package game;

import characters.Monster;
import characters.Player;
import combat.CombatEngine;
import exceptions.InvalidMenuChoiceException;
import inventory.Inventory;
import io.InputUtil;
import items.Item;
import items.Potion;
import world.Room;
import world.WorldMap;
import util.RandomUtil;
import util.WeightedBag;
import java.io.FileNotFoundException;

public class GameEngine {

    private GameState gameState;

    public GameEngine() {
        this.gameState = new GameState();
    }

    public void startNewGame() throws InvalidMenuChoiceException {
        WorldMap worldMap = new WorldMap();
        try {
            worldMap.loadMap("data/rooms.json");
            gameState.setWorldMap(worldMap);
            gameState.setCurrentRoom(worldMap.getRoom(worldMap.getStartRoomId()));
            System.out.println("Map loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: rooms.json not found!");
            return;
        }

        // Collect the player name before creating the Player.
        GameState.playerCreation(gameState);
        
        displayRoom(gameState.getCurrentRoom());
        runGameLoop();
    }

    public void loadExistingGame() {
        WorldMap worldMap = new WorldMap();
        try {
            worldMap.loadMap("data/rooms.json");
            GameState loadedState = io.SaveManager.loadGame(worldMap);
            if (loadedState == null || loadedState.getPlayer() == null) {
                System.out.println("Error: No valid save game found.");
                return;
            }
            this.gameState = loadedState;
            System.out.println("Game loaded successfully!");
            displayRoom(gameState.getCurrentRoom());
            runGameLoop();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void runGameLoop() {
        boolean inGame = true;
        int choice;

        while (inGame) {
            // In-game menu loop.
            System.out.println("""
                    
                    1)  Explore / Move
                    
                    2)  Character Sheet
                    
                    3)  Inventory
                    
                    4)  Save
                    
                    5)  Quit to Main Menu
                    """);

            try {
                choice = InputUtil.getIntInput();
                switch (choice) {
                    case (1): {
                        System.out.println("Enter direction (N/S/E/W): ");
                        String direction = InputUtil.getStringInput().trim().toUpperCase();

                        Room currentRoom = gameState.getCurrentRoom();
                        if(currentRoom.hasNeighbor(direction)){
                            String nextRoomId = currentRoom.getNeighborId(direction);
                            Room nextRoom = gameState.getWorldMap().getRoom(nextRoomId);
                            gameState.setCurrentRoom(nextRoom);
                            System.out.println("\n" + "You moved " + direction);
                            displayRoom(nextRoom);
                            checkEncounter(nextRoom);
                            checkLoot();
                        }
                        else {
                            System.out.println("\n" + "You cannot move that way.");
                        }
                        break;
                    }
                    case (2): {
                        displayCharacterSheet();
                        break;
                    }
                    case (3): {
                        useItem();
                        break;
                    }
                    case (4): {
                        io.SaveManager.saveGame(gameState);
                        break;
                    }
                    case (5): {
                        inGame = false;
                        break;
                    }
                    default: {
                        throw new InvalidMenuChoiceException("In-Game Menu", 1, 5, choice);
                    }
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void displayRoom(Room room) {
        System.out.println("==============================");
        System.out.println("  " + room.getName());
        System.out.println("==============================");
        System.out.println(room.getDescription());
        System.out.println();
        
        String exits = String.join(", ", room.getNeighbors().keySet());
        System.out.println("Exits: " + exits);
    }

    private void checkEncounter(Room room) {
        double roll = RandomUtil.randomDouble();

        if (roll < room.getEncounterRate()) {
            Monster monster = spawnMonster();
            System.out.println("\n⚠ A " + monster.getName() + " appears!");
            CombatEngine combat = new CombatEngine(gameState.getPlayer(), monster);
            combat.startCombat();
        }
    }

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

    private void checkLoot() {
        if (util.RandomUtil.randomDouble() < 0.3) {
            Item item = getRandomItem();
            System.out.println("\n🎁 You found something in the Mist: " + item.getName() + "!");
            gameState.getPlayer().getInventory().addItem(item);
        }
    }

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

    private void displayCharacterSheet() {
        Player p = gameState.getPlayer();
        System.out.println("==============================");
        System.out.println("  📜 CHARACTER SHEET");
        System.out.println("==============================");
        System.out.println("Name:     " + p.getName());
        System.out.println("Level:    " + p.getLevel());
        System.out.println("XP:       " + p.getXp() + "/" + (p.getLevel() * 100));
        System.out.println("HP:       " + p.getHp() + "/" + p.getMaxHp());
        System.out.println("Strength: " + p.getStrength());
        System.out.println("Defense:  " + p.getDefense());
        System.out.println("Magic:    " + p.getMagic());
        System.out.println("Speed:    " + p.getSpeed());
    }

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
