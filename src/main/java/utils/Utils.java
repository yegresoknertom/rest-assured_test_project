package utils;

public class Utils {

    public String randomEmail(){
        int randomInt = (int) (Math.random() * 10000000);
        String email = randomInt + "@testrest-assured.test";
        return email;
    }

}
