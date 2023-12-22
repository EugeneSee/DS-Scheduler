package suibian;

class Task {
    private String methodName;
    private String inputType;
    private String input;
    private long timeOfArrival;
    private long startTime;
    private long completionTime;
    public Task(String methodName, String inputType, String input, long timeOfArrival) {
        this.methodName = methodName;
        this.inputType = inputType;
        this.input = input;
        this.timeOfArrival = timeOfArrival;
        this.startTime = 0;
        this.completionTime = 0;
    }
    public long getTimeOfArrival() {
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

    public void setTimeOfArrival(long timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }
}