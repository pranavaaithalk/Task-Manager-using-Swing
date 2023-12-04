import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagerApp extends JFrame {
    private DefaultListModel<String> toDoListModel, inProgressListModel, completedListModel, deletedListModel;
    private JList<String> toDoList, inProgressList, completedList, deletedList;
    private JTextField taskTextField;
    private JComboBox<String> priorityComboBox;
    private JTable detailsTable;

    public TaskManagerApp() {
        // Initialize frame
        setTitle("Task Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        toDoListModel = new DefaultListModel<>();
        inProgressListModel = new DefaultListModel<>();
        completedListModel = new DefaultListModel<>();
        deletedListModel = new DefaultListModel<>();

        toDoList = new JList<>(toDoListModel);
        inProgressList = new JList<>(inProgressListModel);
        completedList = new JList<>(completedListModel);
        deletedList = new JList<>(deletedListModel);

        taskTextField = new JTextField(20);

        String[] priorities = {"High", "Medium", "Low"};
        priorityComboBox = new JComboBox<>(priorities);

        // Add action listeners for buttons
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        JButton inprogButton = new JButton("Task In-progress");
        inprogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inprogress();
            }
        });

        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        JButton completeButton = new JButton("Mark as Complete");
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsComplete();
            }
        });

        // Layout components using panels and tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("To-Do", createTaskListPanel(toDoList));
        tabbedPane.addTab("In-Progress", createTaskListPanel(inProgressList));
        tabbedPane.addTab("Completed", createTaskListPanel(completedList));
        tabbedPane.addTab("Deleted", createTaskListPanel(deletedList));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Task Description:"));
        inputPanel.add(taskTextField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);
        inputPanel.add(addButton);
        inputPanel.add(inprogButton);
        inputPanel.add(deleteButton);
        inputPanel.add(completeButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Set up the frame
        add(mainPanel);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JPanel createTaskListPanel(JList<String> taskList) {
        JScrollPane scrollPane = new JScrollPane(taskList);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void addTask() {
        String taskDescription = taskTextField.getText();
        String priority = (String) priorityComboBox.getSelectedItem();
        String task = taskDescription + " (Priority: " + priority + ")";
        toDoListModel.addElement(task);
        taskTextField.setText(""); // Clear the text field after adding the task
    }

    private void inprogress()
    {
        inProgressListModel.addElement(toDoList.getSelectedValue());
        toDoListModel.removeElementAt(toDoList.getSelectedIndex());
    }

    private void deleteTask() {
        deletedListModel.addElement(toDoList.getSelectedValue());
        toDoListModel.removeElementAt(toDoList.getSelectedIndex());
    }

    private void markAsComplete() {
        // Implement marking task as complete logic based on the selected tab
        completedListModel.addElement(inProgressListModel.getElementAt(inProgressList.getSelectedIndex()));
        // Then, delete the task from the to-do list
        inProgressListModel.removeElementAt(inProgressList.getSelectedIndex());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerApp();
            }
        });
    }
}
