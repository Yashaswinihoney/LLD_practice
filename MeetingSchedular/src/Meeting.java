public class Meeting {
    private final String meetingId;
    private final long startTime;
    private final long endTime;

    public Meeting(String meetingId, long startTime, long endTime) {
        this.meetingId = meetingId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
