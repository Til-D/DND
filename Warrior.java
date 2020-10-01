public class Warrior extends Player {

	public static final double DEFAULT_STRENGHTS = 50;
	public static final double DEFAULT_WEAPONMULTIPLIER = 1;

	private double strength;
	private double weaponMultiplier;

	public Warrior() {
		super();
	}

	public Warrior(Warrior otherWarrior) {
		super(otherWarrior);
	}

	public Warrior(double strength, double weaponMultiplier) {
		super();
		this.strength = strength;
		this.weaponMultiplier = weaponMultiplier;
	}

	public Warrior(String name, double hitpoints, boolean flightAttack, double strength, double weaponMultiplier) {
		super(name, hitpoints, flightAttack);
		this.strength = strength;
		this.weaponMultiplier = weaponMultiplier;
	}

	@Override
	public double attack() {
		return strength/0.5 * weaponMultiplier;
	}

	@Override
	public void receiveDamage(double damage) {
		reduceHitpoints(damage * Math.random());
	}

	public String toString() {
		return super.toString() + ", strength: " + strength + ", weaponMultiplier: " + weaponMultiplier;
	}
}