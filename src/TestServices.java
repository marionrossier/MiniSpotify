import data.entities.PlanEnum;
import services.UserService;

public class TestServices {
    public static void main(String[] args) {

        UserService userService = new UserService();

        PlanEnum planEnum = PlanEnum.FREE;
        userService.addUser("testUsers", "email", "password", planEnum);
        if(userService.verifyUserAuthentification("testUsers", "password"))
            System.out.println("User verified");
        else
            System.out.println("User not verified");
    }
}
