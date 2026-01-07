package vn.edu.iuh.fit;

import vn.edu.iuh.fit.models.Doctor;

import javax.print.Doc;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("192.168.1.74", 9090);
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("Client is running");
            while (true) {

                System.out.println("1. Find doctor by id");
                System.out.println("2. Get number of doctors by speciality");
                System.out.println("3. Exit");
                System.out.print("Please choose your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        dataOutputStream.writeUTF("FIND_DOCTOR");
                        System.out.print("Enter the doctor id: ");
                        String doctorId = sc.next();
                        sc.nextLine();
                        dataOutputStream.writeUTF(doctorId);
                        dataOutputStream.flush();
                        Doctor doctor = (Doctor) inputStream.readObject();
                        System.out.println(doctor);
                    }

                    case 2 -> {
                        dataOutputStream.writeUTF("GET_NO_OF_DOCTORS_BY_SPECIALITY");
                        System.out.print("Enter the department name: ");
                        String departmentName = sc.nextLine();
                        dataOutputStream.writeUTF(departmentName);
                        dataOutputStream.flush();
                        Map<String, Long> res = (Map<String, Long>) inputStream.readObject();
                        res.forEach((k, v) -> System.out.println(k + ": " + v));
                    }

                    case 3 -> {
                        socket.close();
                        System.out.println("Client is UnConnected");
                        return;
                    }

                    default -> {
                        System.out.println("The choice is not match. Please choose again");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
