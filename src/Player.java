class Player extends Character {
    private int gold;
    private int experience;
    private int level;

    public Player(String name) {
        super(name, 100, 10, 10);// Начальные параметры: здоровье 100,сила 10,ловкость 10
        this.gold = 0;
        this.experience = 0;
        this.level = 1;

    }
    public int getGold() {
        return gold;
    }
    public void addGold(int amount) {
        gold += amount;
    }
    public int getExperience() {
        return experience;
    }
    public void addExperience(int amount) {
        experience += amount;
        checkLevelUp();
    }
    public int getLevel() {
        return level;
    }
    private void checkLevelUp() {
        if (experience >= level * 10) {// Формула для повышения уровня
            experience -= level * 10;
            level++;
            strength += 2;//Увеличиваем силу при повышении урона
            health += 20;//Увеличиваем здоровье
            System.out.println("Поздравляем! Вы достигли уровня " + level + "!");
        }
    }
    public void heal(int amount) {
        health += amount;
        System.out.println("Вы восстановили " + amount + "  здоровья.");
    }
    public void buyPotion(int cost) {
        if (gold >= cost) {
            gold -= cost;
            heal(50);//Восстанавливаем 50 здоровья
        } else {
            System.out.println("У вас недостаточно золота для покупки зелья.");
        }
    }
}
