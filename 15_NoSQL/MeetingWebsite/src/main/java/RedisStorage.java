import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;
import java.util.Date;

import static java.lang.System.out;

public class RedisStorage {

    private RedissonClient redisson;

    private RKeys rKeys;

    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";

    private double getTs() {
        return new Date().getTime();
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        onlineUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void logPageVisit(int user_id) {

        for (int i = 1; i <= user_id; i++) {
            //ZADD ONLINE_USERS
            onlineUsers.add(getTs(), String.valueOf(i));
        }
    }

    public void userOutput(String id) {
        System.out.println("- На главной странице показываем пользователя " + id);
        onlineUsers.add(getTs(), id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void paidService(String id) {
        System.out.println("Пользователь " + id + " оплатил услугу.");
        onlineUsers.add(onlineUsers.firstScore() - 1, id);
    }

    public int random(int min, int max) {
        return ((int) ((Math.random() * (max - min) + min + 1)));
    }

    public void mainPageWebsite(int payment) {
        int paidUser = onlineUsers.size() / payment;
        int maxLimitStart = payment;
        int minLimitStart = 0;
        int[] richUsers = new int[paidUser];
        int[] lastUsers = new int[paidUser];
        for (int i = 0; i < paidUser; i++) {
            int richUser = random(minLimitStart, maxLimitStart);
            int lastUser = random(minLimitStart, maxLimitStart);
            while(richUser <= lastUser) {
                richUser = random(minLimitStart, maxLimitStart);
                lastUser = random(minLimitStart,maxLimitStart);
            }
            richUsers[i] = richUser;
            lastUsers[i] = lastUser;
            minLimitStart = maxLimitStart;
            maxLimitStart = maxLimitStart + maxLimitStart * (i+1);
        }

        for (int j = 1; j <= onlineUsers.size(); j++) {
            for (int i = 0; i < richUsers.length; i++) {
                if (j == lastUsers[i]) {
                    paidService(String.valueOf(richUsers[i]));
                }
            }
            userOutput(onlineUsers.first());
        }

        for (int i = 0; i < richUsers.length; i++) {
            onlineUsers.add(onlineUsers.getScore(String.valueOf(richUsers[i] - 1)) + 1, String.valueOf(richUsers[i]));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
