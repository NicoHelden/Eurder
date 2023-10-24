package be.switchfully;

import be.switchfully.database.EurderDb;
import be.switchfully.domain.user.Role;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String[] args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            String id = EurderDb.usersById.values().stream()
                    .filter(user -> user.getRole().equals(Role.ADMIN))
                    .findFirst().get().getId();
            System.out.println("Initialized Admin with ID: "+ id);

            Quarkus.waitForExit();
            return 0;
        }
    }
}
