import java.util.Scanner;

public class Game {
    private Player player;

    public Game() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя персонажа: ");
        String playerName = scanner.nextLine();
        player = new Player(playerName);

        startGame(scanner);
    }

    private void startGame(Scanner scanner) {
        while (true) {
            System.out.println("\nКуда вы хотите пойти?");
            System.out.println("1. К торговцу");
            System.out.println("2. В тёмный лес");
            System.out.println("3. На выход");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    visitTrader(scanner);
                    break;
                case 2:
                    enterDarkForest(scanner);
                    break;
                case 3:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void visitTrader(Scanner scanner) {
        System.out.println("Вы пришли к торговцу. Что вы хотите сделать?");
        System.out.println("1. Купить зелье лечения (20 золота)");
        System.out.println("2. Вернуться в город");

        int choice = scanner.nextInt();
        if (choice == 1) {
            player.buyPotion(20);
        } else if (choice == 2) {
            System.out.println("Вы вернулись в город.");
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private void enterDarkForest(Scanner scanner) {
        Character monster = Math.random() < 0.5 ? new Goblin() : new Skeleton();
        System.out.println("Вы встретили " + monster.getName() + "! Начинается бой!");

        // Запускаем бой в отдельном потоке
        Thread battleThread = new Thread(() -> startBattleInThread(monster));
        battleThread.start();

        try {
            battleThread.join(); // Ожидаем завершения потока боя
        } catch (InterruptedException e) {
            System.out.println("Произошла ошибка при выполнении боя.");
        }

        if (!player.isAlive()) {
            System.out.println("Игра окончена. Вы погибли.");
            return;
        }

        System.out.println("Что вы хотите сделать?");
        System.out.println("1. Продолжить исследовать лес");
        System.out.println("2. Вернуться в город");

        int choice = scanner.nextInt();
        if (choice == 1) {
            enterDarkForest(scanner);
        } else if (choice == 2) {
            System.out.println("Вы вернулись в город.");
        }
    }

    private void startBattleInThread(Character monster) {
        while (player.isAlive() && monster.isAlive()) {
            // Ход игрока
            synchronized (this) {
                int playerDamage = player.attack();
                monster.setHealth(monster.getHealth() - playerDamage);
                System.out.println("Вы нанесли " + playerDamage + " урона. Здоровье " + monster.getName() + ": " + monster.getHealth());

                if (!monster.isAlive()) {
                    System.out.println("Вы победили " + monster.getName() + "!");
                    player.addGold(10);
                    player.addExperience(5);
                    break;
                }
            }

            // Ход монстра
            synchronized (this) {
                int monsterDamage = monster.attack();
                player.setHealth(player.getHealth() - monsterDamage);
                System.out.println(monster.getName() + " нанёс вам " + monsterDamage + " урона. Ваше здоровье: " + player.getHealth());

                if (!player.isAlive()) {
                    System.out.println("Вы проиграли...");
                    break;
                }
            }

            try {
                Thread.sleep(1000); // Задержка между ходами
            } catch (InterruptedException e) {
                System.out.println("Произошла ошибка во время боя.");
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
