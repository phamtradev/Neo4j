package vn.edu.iuh.fit.daos;

import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;
import vn.edu.iuh.fit.models.Doctor;
import vn.edu.iuh.fit.util.AppUtil;

import java.util.Map;
import java.util.stream.Collectors;

public class DoctorDAO {

    public Doctor findDoctorById(String doctorId) {
        String query =
                """
                MATCH (d:Doctor)
                WHERE d.doctor_id = $doctorId
                RETURN d
                """;
        Map<String, Object> params = Map.of(
            "doctorId", doctorId
        );
        try (Session session = AppUtil.getSession()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);

                if (result.hasNext()) {
                    Node node = result.next().get("d").asNode();

                    return Doctor
                            .builder()
                            .speciality(node.get("speciality").asString())
                            .doctorId(node.get("doctor_id").asString())
                            .phone(node.get("phone").asString())
                            .name(node.get("name").asString())
                            .deptId(node.get("dept_id").asString())
                            .build();
                } else {
                    return null;
                }
            });
        }
    }

    public Map<String, Long> getNoOfDoctorsBySpeciality(String departmentName) {
        String query =
                """
                MATCH (d:Doctor) - [r:BELONG_TO] -> (dp:Department)
                WHERE dp.name = $departmentName
                RETURN d.speciality as speciality, count (d) as total
                """;
        Map<String, Object> params = Map.of(
            "departmentName", departmentName
        );

        try (Session session = AppUtil.getSession()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result
                        .stream()
                        .collect(Collectors.toMap(
                                r -> r.get("speciality").asString(),
                                r -> r.get("total").asLong()
                        ));
            });
        }
    }
}
