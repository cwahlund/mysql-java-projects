package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.exception.DbException;
import projects.service.ProjectService;
import projects.entity.Project;

//import projects.dao.DbConnection;

public class ProjectsApp {

	private Scanner scanner = new Scanner(System.in);

	// @formatter:off
	private List<String> operations = List.of(
		"1) Add a project"	
	);
	// @formatter:on

	ProjectService projectService = new ProjectService();

	public static void main(String[] args) {

		new ProjectsApp().processUserSelections();

	}

	private void processUserSelections() {
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();

				switch (selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
			} catch (Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}

	}

	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");

		while (difficulty < 1 || difficulty > 5) {
			System.out.println("Invalid difficulty number.");
			difficulty = getIntInput("Enter the project difficulty (1-5)");
		}

		String notes = getStringInput("Enter the project notes");
		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}

	private boolean exitMenu() {
		System.out.println("Thank you for using the projects DB, exiting.");
		return true;
	}

	private int getUserSelection() {
		printOperations();

		Integer selection = getIntInput("Enter a menu selection (press Enter to quit)");

		return Objects.isNull(selection) ? -1 : selection;
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}

		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.  Try again.");
		}
	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String line = scanner.nextLine();
		
		return line.isBlank() ? null : line.trim();
	}

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");

		operations.forEach(line -> System.out.println("   " + line));
		
	}

}
