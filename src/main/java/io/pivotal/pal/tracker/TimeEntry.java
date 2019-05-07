package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(long projectId, long userId, LocalDate parse, int i) {
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate parse, int i) {
    }

    public TimeEntry(Object id, long projectId, long userId, LocalDate parse, int i) {
    }

    public TimeEntry() {

    }

    public long getId() {
        return id;
    }

    public boolean equals(Object o) {
//        if (o == null || !(o instanceof TimeEntry) ) {
//            return false;
//        }
//        TimeEntry t = (TimeEntry)o;
//        return this.projectId == t.projectId &&
//                this.userId == t.userId &&
//                this.date == t.date &&
//                this.hours == t.hours;
        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

}
