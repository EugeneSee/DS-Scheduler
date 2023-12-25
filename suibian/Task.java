package suibian;

import java.time.Instant;

class Task {
    private String methodName;
    private String inputType;
    private String input;
    private Instant timeOfArrival;
    private long startTime;
    private long completionTime;
    private long ResponseTime;
    private long turnAroundTime;
    public Task(String methodName, String inputType, String input, Instant timeOfArrival) {
        this.methodName = methodName;
        this.inputType = inputType;
        this.input = input;
        this.timeOfArrival = timeOfArrival;
        this.startTime = 0;
        this.completionTime = 0;
    }
    public Instant getTimeOfArrival() {
        return timeOfArrival;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setTimeOfArrival(Instant timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }
    public long getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(long responseTime) {
        ResponseTime = responseTime;
    }

    public long getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(long turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }
}