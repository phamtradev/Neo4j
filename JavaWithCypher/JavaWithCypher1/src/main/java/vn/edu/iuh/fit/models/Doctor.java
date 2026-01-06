package vn.edu.iuh.fit.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//"labels": [
//    "Doctor"
//  ],
//  "properties": {
//    "speciality": "Dermatology Services",
//    "doctor_id": "DR.001",
//    "phone": "0987.654.321",
//    "name": "John Smith",
//    "dept_id": "DEP001"
//  },
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Doctor {

    private String doctorId;
    private String deptId;
    private String phone;
    private String name;
    private String speciality;
}
