package vn.edu.iuh.fit;

import vn.edu.iuh.fit.daos.DoctorDAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlingClient implements Runnable {

    private Socket socket;
    private DoctorDAO doctorDAO;

    public HandlingClient(Socket socket) {
        this.socket = socket;
        this.doctorDAO = new DoctorDAO();
    }

    @Override
    public void run() {
        try (
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {

            while (true) {
                String command = inputStream.readUTF();

                switch (command) {
                    case "FIND_DOCTOR" -> {
                        String doctorId = inputStream.readUTF();
                        outputStream.writeObject(doctorDAO.findDoctorById(doctorId));
                        outputStream.flush();
                    }

                    case "GET_NO_OF_DOCTORS_BY_SPECIALITY" -> {
                        String departmentName = inputStream.readUTF();
                        outputStream.writeObject(doctorDAO.getNoOfDoctorsBySpeciality(departmentName));
                        outputStream.flush();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
