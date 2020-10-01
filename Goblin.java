public class Goblin extends Player {

	public static final int DEFAULT_HITPOINTS = 10;

	public Goblin() {
		super("anonym goblin", DEFAULT_HITPOINTS, false);
	}

	public Goblin(int hitpoints) {
		super(hitpoints);
	}

	public Goblin(Goblin otherGoblin) {
		super(otherGoblin);
		if(otherGoblin==null) {
			System.out.println("ERROR: null goblin object clone attempt");
			System.exit(0);
		}
	}

	public String toString() {
		return "Goblin, the enemy: " + super.toString();
	}

	@Override
	public double attack() {
		return 10;
	}

	@Override
	public void receiveDamage(double damage) {
		reduceHitpoints(damage);
	}
}