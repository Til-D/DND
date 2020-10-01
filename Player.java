import java.io.Serializable;

public abstract class Player implements Serializable {

	public static final String DEFAULT_NAME = "anonymous";
	public static final double DEFAULT_HITPOINTS = 100;
	public static final boolean DEFAULT_FLIGHTATTACK = false;
	
	private String name;
	private double hitpoints;
	private boolean flightAttack;

	public Player() {
		name = DEFAULT_NAME;
		hitpoints = DEFAULT_HITPOINTS;
		flightAttack = DEFAULT_FLIGHTATTACK;
	}

	public Player(int hitpoints) {
		if(hitpoints<=0) {
			System.out.println("WARNING: player needs to be alive upon birth");
			this.hitpoints = DEFAULT_HITPOINTS;
		} else {
			this.hitpoints = hitpoints;	
		}
		this.name = DEFAULT_NAME;
		this.flightAttack = DEFAULT_FLIGHTATTACK;
	}

	public Player(String name, double hitpoints) {
		this.name = name;
		if(hitpoints<=0) {
			System.out.println("WARNING: player needs to be alive upon birth");
			this.hitpoints = DEFAULT_HITPOINTS;
		} else {
			this.hitpoints = hitpoints;	
		}
		flightAttack = DEFAULT_FLIGHTATTACK;
	}

	public Player(String name, double hitpoints, boolean flightAttack) {
		this.name = name;

		if(hitpoints<=0) {
			System.out.println("WARNING: player needs to be alive upon birth");
			this.hitpoints = DEFAULT_HITPOINTS;
		} else {
			this.hitpoints = hitpoints;	
		}
		
		this.flightAttack = flightAttack;
	}

	public Player(Player otherPlayer) {
		if(otherPlayer==null) {
			System.out.println("ERROR: cannot copy null player");
			System.exit(0);
		}

		this.name = otherPlayer.getName();
		this.hitpoints = otherPlayer.getHitpoints();
		this.flightAttack = otherPlayer.canFlightAttack();
	}

	// GETTER
	public String getName() {
		return name;
	}

	public double getHitpoints() {
		return hitpoints;
	}

	public boolean canFlightAttack() {
		return flightAttack;
	}

	public boolean isAlive() {
		return getHitpoints()>0;
	}

	// SETTER
	public void setName(String name) {
		this.name = name;
	}

	public void setHitpoints(double hitpoints) {
		if(hitpoints<=0) {
			System.out.println("WARNING: player needs to be alive upon birth");
			this.hitpoints = DEFAULT_HITPOINTS;
		} else {
			this.hitpoints = hitpoints;	
		}
	}

	public void setFlightAttack(boolean flightAttack) {
		this.flightAttack = flightAttack;
	}

	public String toString() {
		return name + " with " + hitpoints + " hipoints, can attack flying objects: " + flightAttack;
	}

	public void reduceHitpoints(double damage) {
		hitpoints-=damage;
	}


	public abstract double attack();

	public abstract void receiveDamage(double damage);

	public boolean equals(Player otherPlayer) {
		if(otherPlayer==null) {
			return false;
		}

		return name.equals(otherPlayer.getName()) && hitpoints==otherPlayer.getHitpoints() && flightAttack==otherPlayer.canFlightAttack();
	}
}