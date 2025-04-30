abstract class Character {
    protected String name;
    protected int health;
    protected int strength;
    protected int agility;

    public Character(String name, int health, int strength, int agility) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.agility = agility;
    }
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = Math.max(health, 0);//Здоровье не может быть меньше 0
    }
    public int getStrength() {
        return strength;
    }
    public int getAgility() {
        return agility;
    }
    public boolean isAlive() {
        return health > 0;
    }
    public int attack() {
        if (Math.random() * 100 < agility * 3) {
            //Успешная атака
            if (Math.random() < 0.2) {
                // 20% шанс критического удара
                System.out.println(name + " наносит критический удар!");
                return strength * 2;
            }
            return strength;
        } else {
            // Промах
            System.out.println(name + " промахнулся!");
            return 0;
        }
    }

}