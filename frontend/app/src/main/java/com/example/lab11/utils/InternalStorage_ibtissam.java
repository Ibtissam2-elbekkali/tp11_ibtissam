package com.example.lab11.utils;

import android.content.Context;
import com.example.lab11.model.Student_ibtissam;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class InternalStorage_ibtissam {
    private static final String FILE_NAME_ibtissam = "students_ibtissam.json";

    public static void saveStudents_ibtissam(Context context_ibtissam, List<Student_ibtissam> list_ibtissam) throws Exception {
        JSONArray array_ibtissam = new JSONArray();
        for (Student_ibtissam s_ibtissam : list_ibtissam) {
            JSONObject obj_ibtissam = new JSONObject();
            obj_ibtissam.put("id", s_ibtissam.id_ibtissam);
            obj_ibtissam.put("name", s_ibtissam.name_ibtissam);
            obj_ibtissam.put("age", s_ibtissam.age_ibtissam);
            array_ibtissam.put(obj_ibtissam);
        }
        String json_ibtissam = array_ibtissam.toString();
        context_ibtissam.openFileOutput(FILE_NAME_ibtissam, Context.MODE_PRIVATE).write(json_ibtissam.getBytes(StandardCharsets.UTF_8));
    }

    public static List<Student_ibtissam> loadStudents_ibtissam(Context context_ibtissam) {
        try {
            byte[] bytes_ibtissam = context_ibtissam.openFileInput(FILE_NAME_ibtissam).readAllBytes();
            String json_ibtissam = new String(bytes_ibtissam, StandardCharsets.UTF_8);
            JSONArray array_ibtissam = new JSONArray(json_ibtissam);
            List<Student_ibtissam> list_ibtissam = new ArrayList<>();
            for (int i = 0; i < array_ibtissam.length(); i++) {
                JSONObject obj_ibtissam = array_ibtissam.getJSONObject(i);
                list_ibtissam.add(new Student_ibtissam(obj_ibtissam.getInt("id"), obj_ibtissam.getString("name"), obj_ibtissam.getInt("age")));
            }
            return list_ibtissam;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
