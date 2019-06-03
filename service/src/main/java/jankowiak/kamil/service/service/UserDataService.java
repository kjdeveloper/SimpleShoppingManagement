package jankowiak.kamil.service.service;

import jankowiak.kamil.exceptions.ExceptionCode;
import jankowiak.kamil.exceptions.MyException;

import java.util.Scanner;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    public int getInt(String message){
        System.out.println(message);

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.INVALID_NUMBER, "Invalid option number");
        }
        return Integer.parseInt(text);
    }

    public void close(){
        if (sc != null){
            sc.close();
            sc = null;
        }
    }
}
