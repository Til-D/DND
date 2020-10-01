import java.util.ArrayList;
import java.io.Serializable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.Scanner;

/**
	Game Logic and Program Entry 
*/
public class DNDGame {

	private ArrayList<Player> players;
	private ArrayList<Player> villains;
	private Scanner scanner;

	public enum CharacterClass {WARRIOR, WIZZARD};


	public DNDGame(Scanner scanner) {
		this.scanner = scanner;
		players = new ArrayList<Player>();
		villains = new ArrayList<Player>();
	}

	public void loadGame(String filename) {

		ObjectInputStream inputStream = null;

		try 
		{
			inputStream = new ObjectInputStream(new FileInputStream(filename));
			GameState gameState = (GameState) inputStream.readObject();
			players = gameState.players;
			villains = gameState.villains;

			System.out.println("Game loaded: ");
			printPlayers();
			printVillains();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("Could not open file: " + filename);
		}
		catch(IOException e)
		{
			System.out.println("Could not read from file.");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Class not found.");
		}
	}

	public void initPlayers() {
		System.out.println("How many players?");	
		int playerCount = Integer.valueOf(scanner.nextLine());

		while(playerCount>0) {

			CharacterClass avatarClass;
			Player player = new Warrior();
			String name;
			int hitpoints;

			System.out.println("Choose your avatar: ");
			for (CharacterClass charClass : CharacterClass.values()) {
				System.out.println("-" + charClass);	
			}

			String avatar = scanner.nextLine().toUpperCase();
			try {
				
				avatarClass = CharacterClass.valueOf(avatar);

				switch(avatarClass) {
					case WARRIOR:

						System.out.println("What's the name of your warrior?");
						name = scanner.nextLine();

						System.out.println("How many hitpoints would you like to assign?");
						hitpoints = Integer.valueOf(scanner.nextLine());

						player = new Warrior(name, hitpoints, false, Warrior.DEFAULT_STRENGHTS, Warrior.DEFAULT_WEAPONMULTIPLIER);

						break;
					case WIZZARD:

						System.out.println("What's the name of your wizzard?");
						name = scanner.nextLine();

						System.out.println("How many hitpoints would you like to assign?");
						hitpoints = Integer.valueOf(scanner.nextLine());

						System.out.println("How many fireballs does your wizzard have?");
						int fireballs = Integer.valueOf(scanner.nextLine());

						player = new Wizzard(name, hitpoints, fireballs, Wizzard.DEFAULT_FIREBALL_DAMAGE);

						break;
				}

				System.out.println("new player created: " + player.toString());
				players.add(player);

			} catch (IllegalArgumentException e) {
				System.out.println("ERROR: " + avatar + " is not a player class");
			}
			playerCount--;
		}
	}

	public void initVillains() {
		System.out.println("How many villains?");	
		int villainCount = Integer.valueOf(scanner.nextLine());

		System.out.println("How many hitpoints for each villain?");
		int hitpoints = Integer.valueOf(scanner.nextLine());

		while(villainCount>0) {
			villains.add(new Goblin(hitpoints));
			villainCount--;
		}
	}

	public void printPlayers() {
		System.out.println("Player Characters in this game:");
		for(int i=0; i<players.size(); i++) {
			System.out.println(players.get(i));
		}
	}

	public void printVillains() {
		System.out.println("Villains in this game:");
		for(int i=0; i<villains.size(); i++) {
			System.out.println(villains.get(i));
		}
	}

	public void battle() {

		if(players.size()<=0 || villains.size()<=0)
			System.out.println("No two parties to do battle with.");
		else {

			System.out.println("+battle started");

			boolean ongoing = true;
			while(ongoing) {

				for (Player player: players) {

					// players always attack the weakest opponent
					Player target = villains.get(0);
					for (int i=1; i<villains.size(); i++) {

						if(!target.isAlive())
							target = villains.get(i);
						else {
							if(villains.get(i).getHitpoints()<target.getHitpoints())
								target = villains.get(i);
						}
					}

					double damage = player.attack();
					target.receiveDamage(damage);
					System.out.println(player.getName() + " hitting " + target.getName() + ": " + damage);
				}

				for (Player villain: villains) {

					// players always attack the weakest opponent
					Player target = players.get(0);
					for (int i=1; i<players.size(); i++) {

						if(!target.isAlive())
							target = players.get(i);
						else {
							if(players.get(i).getHitpoints()<target.getHitpoints())
								target = players.get(i);
						}
					}

					double damage = villain.attack();
					target.receiveDamage(damage);
					System.out.println(villain.getName() + " hitting " + target.getName() + ": " + damage);

				}

				// check our loop condition: ongoing?
				int characterCount = 0;
				for (Player player: players) {
					if(player.isAlive())
						characterCount++;
				}

				int villainCount = 0;
				for (Player villain: villains) {
					if(villain.isAlive())
						villainCount++;
				}

				if(characterCount==0) {
					ongoing = false;
					System.out.println("Game over. Goblins won!");
				}

				if(villainCount==0) {
					ongoing = false;
					System.out.println("Well done. Goblins defated!");
				}
			}
		}

	}

	public void saveStatus(String filename) {

		ObjectOutputStream outputStream = null;		

		try
		{
			outputStream = new ObjectOutputStream(new FileOutputStream(filename));

			GameState gameState = new GameState(players, villains);

			outputStream.writeObject(gameState);
			outputStream.close();
			System.out.println("Game state written to: " + filename);
		}
		catch(IOException e)
		{
			System.out.println("Could not open file: " + filename);
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		DNDGame game = new DNDGame(scanner);

		String filename = "players.dat";
		
		System.out.println("Load players from file? (y/n)");
		String resp = scanner.nextLine().toLowerCase();
		if(resp.equals("y")) {
			File fileObject = new File(filename);
			if(fileObject.exists()) {
				game.loadGame(filename);
			} 
			else {
				System.out.println("No game state found: " + filename);
			}
		} 
		else {
			game.initPlayers();
			game.initVillains();
			game.saveStatus(filename);
		}

		game.battle();

	}
}

class GameState implements Serializable {
	public ArrayList<Player> players;
	public ArrayList<Player> villains;	

	public GameState (ArrayList<Player> players, ArrayList<Player> villains) {
		this.players = players;
		this.villains = villains;
	}
}