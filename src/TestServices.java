import data.entities.PlanEnum;
import services.login.CreateUser;
import services.login.VerifyUser;

public class TestServices {
    public static void main(String[] args) {
        PlanEnum planEnum = PlanEnum.FREE;
        CreateUser.addUser("testUsers", "email", "password", planEnum);
        if(VerifyUser.verifyUser("testUsers", "password"))
            System.out.println("User verified");
        else
            System.out.println("User not verified");
    }
}
