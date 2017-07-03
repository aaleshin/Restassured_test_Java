package objects;

import java.util.HashMap;
import java.util.Map;

public class Issue {
    private Map<String, String> issue;

    public Issue() {
        issue = new HashMap<>();
    }

    public void setProjectId(int id) {
        issue.put("project_id", Integer.toString(id));
    }

    public void setSubject(String name) {
        issue.put("subject", name);
    }

    public void setPriorityId(int id) {
        issue.put("priority", Integer.toString(id));
    }

    public void setSubjectName(String subjectName) {
        issue.put("subject", subjectName);
    }

    public void setDescription(String notesName) {
        issue.put("description", notesName);
    }
}



