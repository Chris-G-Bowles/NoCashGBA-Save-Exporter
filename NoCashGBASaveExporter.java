//No$GBA Save Exporter

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class NoCashGBASaveExporter {
	public static void main(String[] args) {
		System.out.println("* No$GBA Save Exporter *");
		if (args.length != 0 && args.length != 3) {
			error("This program's usage is as follows:\n" +
					"java NoCashGBASaveExporter\n" +
					"java NoCashGBASaveExporter <input save file location> <output size option> " +
					"<output save file location>");
		}
		Scanner input = new Scanner(System.in);
		String inputSaveFileLocation;
		if (args.length == 0) {
			System.out.print("Enter the input save file's location: ");
			inputSaveFileLocation = input.nextLine();
		} else {
			inputSaveFileLocation = args[0];
		}
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(inputSaveFileLocation);
		} catch (Exception e) {
			error(inputSaveFileLocation + " is not a valid file.");
		}
		String outputSizeString;
		if (args.length == 0) {
			System.out.println("Select this save file's output size:");
			System.out.println("1) 128 kb (e-Reader, Pokemon Ruby/Sapphire/Emerald/FireRed/LeafGreen, " +
					"Super Mario Advance 4)");
			System.out.println("2) 64 kb (Kirby & the Amazing Mirror, Mario vs Donkey Kong, " +
					"Mega Man Zero 3, Metroid Fusion/Zero Mission, Mother 3, Sonic Advance 1-3)");
			System.out.println("3) 32 kb (Kirby: Nightmare in Dream Land, Mega Man Zero 1-2)");
			System.out.println("4) 16 kb (???)");
			System.out.println("5) 8 kb (Mario & Luigi: Superstar Saga, Mega Man & Bass/Zero 4, " +
					"Super Mario Advance 2-3)");
			System.out.println("6) 4 kb (???)");
			System.out.println("7) 2 kb (???)");
			System.out.println("8) 1 kb (???)");
			System.out.println("9) 512 b (Super Mario Advance 1, Yoshi Topsy Turvy)");
			System.out.print("Output size option: ");
			outputSizeString = input.nextLine();
		} else {
			outputSizeString = args[1];
		}
		Scanner lineInput = new Scanner(outputSizeString);
		if (!lineInput.hasNextInt()) {
			error("Invalid output size input.");
		}
		int outputSizeOption = lineInput.nextInt();
		if (outputSizeOption < 1 || outputSizeOption > 9) {
			error("Invalid output size option.");
		}
		lineInput.close();
		int outputSaveFileSize = (int)Math.pow(2, 18 - outputSizeOption);
		String outputSaveFileLocation;
		if (args.length == 0) {
			System.out.print("Enter the output save file's location: ");
			outputSaveFileLocation = input.nextLine();
		} else {
			outputSaveFileLocation = args[2];
		}
		File outputSaveFileDirectory = new File(outputSaveFileLocation).getParentFile();
		if (outputSaveFileDirectory != null && !outputSaveFileDirectory.exists() && outputSaveFileDirectory.mkdirs()) {
			System.out.println(outputSaveFileDirectory + " was created.");
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputSaveFileLocation);
		} catch (Exception e) {
			error("Unable to create " + outputSaveFileLocation + ".");
		}
		input.close();
		int token;
		ArrayList<Integer> inputSaveFile = new ArrayList<>();
		do {
			try {
				token = inputStream.read();
			} catch (Exception e) {
				token = -1;
			}
			if (token >= 0 && token <= 255) {
				inputSaveFile.add(token);
			}
		} while (token >= 0 && token <= 255);
		try {
			inputStream.close();
		} catch (Exception e) {
			error("Unable to close inputStream.");
		}
		int inputSaveFileSize = 139632;
		if (inputSaveFile.size() != inputSaveFileSize) {
			error(inputSaveFileLocation + " is not " + inputSaveFileSize + " bytes in size.");
		}
		int outputOffset = 76;
		for (int i = 0; i < outputSaveFileSize; i++) {
			try {
				outputStream.write(inputSaveFile.get(i + outputOffset));
			} catch (Exception e) {
				error("Unable to write the input save file to " + outputSaveFileLocation + ".");
			}
		}
		try {
			outputStream.close();
		} catch (Exception e) {
			error("Unable to close outputStream.");
		}
		System.out.println("Success: " + outputSaveFileLocation + " was created!");
	}
	
	private static void error(String message) {
		System.out.println("Error: " + message);
		System.exit(1);
	}
}
