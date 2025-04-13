import data.entities.PlanEnum;
import services.user.CreateUser;
import services.user.VerifyUser;

public class TestServices {
    public static void main(String[] args) {

        VerifyUser verifyUser = new VerifyUser();
        CreateUser createUser = new CreateUser();

        PlanEnum planEnum = PlanEnum.FREE;
        createUser.addUser("testUsers", "email", "password", planEnum);
        if(verifyUser.verifyUser("testUsers", "password"))
            System.out.println("User verified");
        else
            System.out.println("User not verified");
    }
}
