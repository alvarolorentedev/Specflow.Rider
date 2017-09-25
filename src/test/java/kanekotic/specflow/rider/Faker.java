package kanekotic.specflow.rider;

import java.util.UUID;

public class Faker {
    public static String getRandomString(){
        return UUID.randomUUID().toString();
    }
}
