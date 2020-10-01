public class Wizzard extends Player {

	public static final int DEFAULT_FIREBALL_DAMAGE = 20;

	private int fireballs;
	private int fireballDamage;

	public Wizzard() {
		super();
		fireballs = 2;
		fireballDamage = DEFAULT_FIREBALL_DAMAGE;
	}

	public Wizzard(int fireballs, int fireballDamage) {
		this.fireballs = fireballs;
		this.fireballDamage = fireballDamage;
	}

	public Wizzard(String name, double hitpoints, int fireballs, int fireballDamage) {
		super(name, hitpoints);
		this.fireballs = fireballs;
		this.fireballDamage = fireballDamage;
	}

	public Wizzard(Wizzard otherWizzard) {
		super(otherWizzard);
	}

	@Override
	public double attack() {
		return Math.random()*fireballDamage;
	}

	@Override
	public void receiveDamage(double damage) {
		reduceHitpoints(damage * Math.random());
	}

	public String toString() {
		return super.toString() + ", fireballs: " + fireballs + ", fireballDamage: " + fireballDamage;
	}
}