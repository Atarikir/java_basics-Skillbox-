public class Loader {

    private static final int NUMBER_OF_USERS = 20;
    private static final int PAID_USER = 10;

    public static void main(String[] args) {

        RedisStorage redis = new RedisStorage();
        redis.init();
        redis.logPageVisit(NUMBER_OF_USERS);

        for(;;) {
            redis.mainPageWebsite(PAID_USER);
        }

    }
}
