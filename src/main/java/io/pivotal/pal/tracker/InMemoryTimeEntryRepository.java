package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private int nextId = 1;
    private TimeEntry val;

    private List<TimeEntry> data = new ArrayList<>();

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(nextId++);
        val = timeEntry;
        data.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        // return val;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                return data.get(i);
            }
        }
        return null;
    }

    public List list() {
        return data;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        int idx = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                idx = i;
            }
        }
        if (idx == -1) {
            return null;
        }
        return data.get(idx);
    }

    public void delete(long id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                data.remove(i);
            }
        }
    }
}
