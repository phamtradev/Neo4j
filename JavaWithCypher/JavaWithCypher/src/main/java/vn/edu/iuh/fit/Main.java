package vn.edu.iuh.fit;

import vn.edu.iuh.fit.daos.DoctorDAO;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DoctorDAO doctorDAO = new DoctorDAO();
        System.out.println(doctorDAO.findDoctorById("DR.001"));
        doctorDAO.getNoOfDoctorsBySpeciality("Internal Medicine")
                .forEach((k, v) -> System.out.println(k + ": " + v));
    }
}