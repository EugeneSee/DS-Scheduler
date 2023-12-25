//The reason you're seeing the same value multiple times is that you're calling Instant.now()
// and converting it to the ZonedDateTime in the Malaysia timezone repeatedly in quick succession.
// The precision of Instant is up to nanoseconds, but if you execute these lines in a loop or quickly one after another,
// the resolution of the system clock might not be high enough to produce different nanosecond values.*/
package suibian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class SS extends JFrame {
    private DefaultTableModel tableModel;
    private JButton nextButton;
    private int currentIndex = -1;
    private String[] systemNames = {"Queue System", "Linked List System", "Stack System"};
    private CustomDataStructure<Task>[] systems = new CustomDataStructure[]{new CustomQueue<>(), new CustomLinkedList<>(), new CustomStack<>()};
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS SSSSSS");
    static ZoneId malaysiaTimeZone = ZoneId.of("Asia/Kuala_Lumpur");
    static ZonedDateTime systemStartTime;
    long totalResponseTime;
    long totalTurnaroundTime;
    private long[] AverageResponseTimeArray;
    private long[] AverageTurnaroundTimeArray;

    public SS() {
        AverageResponseTimeArray = new long[systems.length];
        AverageTurnaroundTimeArray = new long[systems.length];
        initializeUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SS::new);
    }

    private void initializeUI() {
        // Set up JFrame
        setTitle("Task Scheduler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create table model with columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Method Name");
        tableModel.addColumn("Input Type");
        tableModel.addColumn("Input");
        tableModel.addColumn("Arrival Time");
        tableModel.addColumn("Execution Time");
        tableModel.addColumn("Completion Time");
        tableModel.addColumn("Response Time");
        tableModel.addColumn("Turnaround Time");

        // Create JTable
        JTable table = new JTable(tableModel);

        // Create JScrollPane to hold the JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Create Next button only
        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> showNextSystem());

        // Removed Back button from the panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Adjusted layout for Next button
        buttonPanel.add(nextButton);

        // Create panel for table and buttonPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add mainPanel to the JFrame
        getContentPane().add(mainPanel);

        // Display the JFrame
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);

        // Start processing tasks for the first system
        showNextSystem();
    }

    private void readTasksFromFile(CustomDataStructure<Task> dataStructure) {
        systemStartTime = Instant.now().atZone(malaysiaTimeZone);
        String filepath = "C:\\Users\\User\\OneDrive - Universiti Malaya\\IdeaProjects\\Assignment\\src\\DS-Scheduler\\suibian\\tasks.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String methodName = parts[0];
                    String inputType = parts[1];
                    String input = parts[2];

                    // Create a Task object for the current line
                    Task task = new Task(methodName, inputType, input, Instant.now().atZone(malaysiaTimeZone).toInstant());

                    dataStructure.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processTasks(CustomDataStructure<Task> schedulingSystem) {
        Task task;
        totalResponseTime = 0;
        totalTurnaroundTime = 0;
        int totalTasks = schedulingSystem.getSize();

        while ((task = schedulingSystem.remove()) != null) {
            Instant arrivalTime = task.getTimeOfArrival();
            Instant executionTime = Instant.now();
            Duration responseDuration = Duration.between(arrivalTime, executionTime);

            long responseTimeNanos = responseDuration.toNanos();
            Instant responseTime = Instant.ofEpochSecond(0, responseTimeNanos);

            executeTask(task);

            Instant completionTime = Instant.now();
            Duration turnaroundDuration = Duration.between(arrivalTime, completionTime);

            long turnaroundTimeNanos = turnaroundDuration.toNanos();
            Instant turnaroundTime = Instant.ofEpochSecond(0, turnaroundTimeNanos);

            tableModel.addRow(new Object[]{
                    task.getMethodName(),
                    task.getInputType(),
                    task.getInput(),
                    formatTime(arrivalTime),
                    formatTime(executionTime),
                    formatTime(completionTime),
                    formatTime(responseTime),
                    formatTime(turnaroundTime)
            });

            totalResponseTime += responseTimeNanos;
            totalTurnaroundTime += turnaroundTimeNanos;
        }
        AverageResponseTimeArray[currentIndex] = totalResponseTime / totalTasks;
        AverageTurnaroundTimeArray[currentIndex] = totalTurnaroundTime / totalTasks;

        ZonedDateTime systemEndTime = Instant.now().atZone(malaysiaTimeZone); // Capture the end time
        displaySystemStatistics(schedulingSystem.getName(), systemStartTime, systemEndTime, AverageResponseTimeArray[currentIndex], AverageTurnaroundTimeArray[currentIndex], totalTasks);
    }

    private static void executeTask(Task task) {
        // Simulate task execution
        task.setStartTime(System.nanoTime());
        switch (task.getMethodName()) {
            case "fib":
                StarterPack.fib(Integer.parseInt(task.getInput()));
                break;
            case "isPrime":
                StarterPack.isPrime(Long.parseLong(task.getInput()));
                break;
            case "longestPalSubstr":
                StarterPack.longestPalSubstr(task.getInput());
                break;
            case "sumOfDigitsFrom1ToN":
                StarterPack.sumOfDigitsFrom1ToN(Integer.parseInt(task.getInput()));
                break;
            case "getNthUglyNo":
                StarterPack.getNthUglyNo(Integer.parseInt(task.getInput()));
                break;
            default:
                // Handle other cases as needed
                break;
        }
        // Record the actual completion time
        task.setCompletionTime(System.nanoTime());
    }

    private static String formatTime(Instant instant) {
        long nanoseconds = instant.getNano();

        long milliseconds = nanoseconds / 1_000_000;
        nanoseconds = nanoseconds % 1_000_000;

        long seconds = instant.getEpochSecond() % 60;

        DecimalFormat df = new DecimalFormat("0");

        return String.format("%s.%03d%06d", df.format(seconds), milliseconds, nanoseconds);
    }

    private static String formatTime(long nanoseconds) {
        long milliseconds = nanoseconds / 1_000_000;
        nanoseconds = nanoseconds % 1_000_000;

        long seconds = milliseconds / 1_000;
        milliseconds = milliseconds % 1_000;

        DecimalFormat df = new DecimalFormat("0");

        return String.format("%d.%03d%06d", seconds, milliseconds, nanoseconds);
    }

    private void displaySystemStatistics(String systemName, ZonedDateTime systemStartTime, ZonedDateTime systemEndTime, long averageResponseTime, long averageTurnaroundTime, int totalTasks) {
        String message = String.format("%s Statistics:\n" +
                        "System Start Time: %s\n" +
                        "System End Time: %s\n" +
                        "Average Response Time: %s\n" +
                        "Average Turnaround Time: %s",
                systemName,
                formatter.format(systemStartTime),
                formatter.format(systemEndTime),
                formatTime(averageResponseTime),
                formatTime(averageTurnaroundTime));

        JOptionPane.showMessageDialog(this, message, "Task Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displaySummary() {
        StringBuilder summaryMessage = new StringBuilder("Summary Statistics:\n");

        for (int i = 0; i < systems.length; i++) {
            String systemName = systems[i].getName();

            // Append system summary to the message
            summaryMessage.append(String.format("%s:\n", systemName));
            summaryMessage.append(String.format("  Average Response Time: %s\n", formatTime(AverageResponseTimeArray[i])));
            summaryMessage.append(String.format("  Average Turnaround Time: %s\n\n", formatTime(AverageTurnaroundTimeArray[i])));
        }

        // Display the summary
        JOptionPane.showMessageDialog(this, summaryMessage.toString(), "Task Statistics", JOptionPane.INFORMATION_MESSAGE);

        // Reset arrays for the next round
       /* totalResponseTimeArray = new long[systems.length];
        totalTurnaroundTimeArray = new long[systems.length];*/
        System.exit(0);
    }

    private void showNextSystem() {
        // Move to the next system
        currentIndex = currentIndex + 1;

        // Check if there are more systems to process
        if (currentIndex < systems.length) {
            // Reset data for the current system
            clearTable();

            // Read and process tasks for the current system
            readTasksFromFile(systems[currentIndex]);
            processTasks(systems[currentIndex]);
        } else {
            // If all systems are processed, display the summary
            displaySummary();
        }
    }
    private void clearTable() {
        tableModel.setRowCount(0);
    }
}

