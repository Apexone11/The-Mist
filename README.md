# 🌫️ The Mist

> *A text-based dungeon RPG built in Java — step into the fog and survive.*

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-In%20Development-yellow)
![License](https://img.shields.io/badge/License-MIT-green)
![Platform](https://img.shields.io/badge/Platform-Console-lightgrey)

---

## 📖 About

**The Mist** is a console-based role-playing game where players explore a fog-shrouded world, battle monsters and
bosses, manage an inventory of weapons and potions, and fight to survive. Built from the ground up in Java as a
semester-long project, each week introduces new features — from combat and exploration to save/load systems and a full
settings menu.

---

## ✨ Features

| Feature                                  | Status         |
|------------------------------------------|----------------|
| Main menu (Start, Load, Settings, Quit)  | ✅ Complete     |
| Player creation                          | ✅ Complete     |
| In-game menu loop                        | ✅ Complete     |
| Custom exception handling                | ✅ Complete     |
| Input validation utilities               | ✅ Complete     |
| Character system (Player, Monster, Boss) | 🔧 In Progress |
| World map & room exploration             | 🔧 In Progress |
| Combat engine with AI controller         | 🔧 In Progress |
| Item system (Weapons & Potions)          | 🔧 In Progress |
| Inventory management                     | 🔧 In Progress |
| Save / Load system                       | 🔧 In Progress |
| Settings manager                         | 🔧 In Progress |
| Weighted random loot drops               | 🔧 In Progress |

---

## 🗂️ Project Structure

```
The Mist/
├── src/
│   ├── main/
│   │   └── main.java           # Entry point
│   ├── game/
│   │   ├── GameEngine.java     # Core game loop
│   │   ├── GameState.java      # Tracks global state
│   │   └── Menu.java           # Main menu controller
│   ├── characters/
│   │   ├── Player.java         # Player character
│   │   ├── Monster.java        # Enemy entities
│   │   └── Boss.java           # Boss enemies
│   ├── combat/
│   │   ├── CombatEngine.java   # Turn-based combat logic
│   │   ├── AIController.java   # Enemy AI behavior
│   │   └── Action.java         # Represents combat actions
│   ├── items/
│   │   ├── Item.java           # Base item class
│   │   ├── Weapon.java         # Weapon items
│   │   └── Potion.java         # Consumable potions
│   ├── inventory/
│   │   └── Inventory.java      # Player inventory
│   ├── world/
│   │   ├── Room.java           # Individual room/location
│   │   └── WorldMap.java       # Map layout & navigation
│   ├── io/
│   │   ├── InputUtil.java      # Console input helpers
│   │   ├── SaveManager.java    # Save/load file I/O
│   │   └── SettingsManager.java# Persistent settings
│   ├── util/
│   │   ├── RandomUtil.java     # RNG helpers
│   │   └── WeightedBag.java    # Weighted probability drops
│   └── exceptions/
│       ├── InvalidMenuChoiceException.java
│       ├── InvalidSaveFormatException.java
│       ├── MapLoadException.java
│       └── SaveNotFoundException.java
└── data/
    └── rooms.json              # Room definitions (data-driven)
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21+** — [Download here](https://adoptium.net/)
- **IntelliJ IDEA** (recommended) or any Java IDE

### Running the Game

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/the-mist.git
   cd the-mist
   ```

2. **Open in IntelliJ IDEA**
    - File → Open → select the project folder
    - Wait for indexing to complete

3. **Run the game**
    - Navigate to `src/main/main.java`
    - Click the green **Run** button or press `Shift+F10`

---

## 🗺️ Development Roadmap

The project follows a weekly delivery schedule. Here's the plan:

| Week | Milestone                                            |
|------|------------------------------------------------------|
| 1    | Project setup, main menu, game loop skeleton         |
| 2    | Player creation, custom exceptions, input validation |
| 3    | Character sheet, Player stats                        |
| 4    | World map, room exploration                          |
| 5    | Combat engine, monster encounters                    |
| 6    | Boss fights, AI controller                           |
| 7    | Item system — weapons & potions                      |
| 8    | Inventory management                                 |
| 9    | Save / Load system                                   |
| 10   | Settings manager                                     |
| 11   | Weighted loot drops, balancing                       |
| 12   | Polish, JavaDoc, final submission                    |

---

## 🧰 Tech Stack

- **Language:** Java 21
- **IDE:** IntelliJ IDEA
- **Data format:** JSON (`rooms.json`)
- **Libraries:** JetBrains Annotations

---

## 👤 Author

**Abdul Rahman Fornah**

- Email: aforna1@umbc.edu
- University of Maryland, Baltimore County (UMBC)

---

## 📄 License

This project is licensed under the MIT License.

---

*The fog rolls in. Are you ready?* 🌫️
