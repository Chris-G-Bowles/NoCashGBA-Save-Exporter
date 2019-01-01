//No$GBA Save Exporter

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class NoCashGBASaveExporter {
	public static void main(String[] args) {
		System.out.println("* No$GBA Save Exporter *");
		if (args.length == 0 || args.length == 3) {
			Scanner input = new Scanner(System.in);
			String inputSaveFileLocation;
			if (args.length == 0) {
				System.out.print("Enter the input save file's location: ");
				inputSaveFileLocation = input.nextLine();
			} else {
				inputSaveFileLocation = args[0];
			}
			String outputSizeOption;
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
				outputSizeOption = input.nextLine();
			} else {
				outputSizeOption = args[1];
			}
			String outputSaveFileLocation;
			if (args.length == 0) {
				System.out.print("Enter the output save file's location: ");
				outputSaveFileLocation = input.nextLine();
			} else {
				outputSaveFileLocation = args[2];
			}
			input.close();
			try {
				FileInputStream inputStream = new FileInputStream(inputSaveFileLocation);
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
				inputStream.close();
				int inputSaveFileSize = 139632;
				if (inputSaveFile.size() == inputSaveFileSize) {
					if (isValidInteger(outputSizeOption) && Integer.parseInt(outputSizeOption) >= 1 &&
							Integer.parseInt(outputSizeOption) <= 9) {
						int outputSaveFileSize = (int)Math.pow(2, 18 - Integer.parseInt(outputSizeOption));
						try {
							FileOutputStream outputStream = new FileOutputStream(outputSaveFileLocation);
							int outputOffset = 76;
							for (int i = 0; i < outputSaveFileSize; i++) {
								outputStream.write(inputSaveFile.get(i + outputOffset));
							}
							outputStream.close();
							System.out.println("Success: " + outputSaveFileLocation + " was created!");
						} catch (Exception e) {
							System.out.println("Error: Could not create " + outputSaveFileLocation + ".");
						}
					} else {
						System.out.println("Error: Invalid output size option.");
					}
				} else {
					System.out.println("Error: " + inputSaveFileLocation + " is not " + inputSaveFileSize +
							" bytes in size.");
				}
			} catch (Exception e) {
				System.out.println("Error: " + inputSaveFileLocation + " not found.");
			}
		} else {
			System.out.println("This program's usage is as follows:");
			System.out.println("java NoCashGBASaveExporter");
			System.out.println("java NoCashGBASaveExporter <input save file location> <output size option> " +
					"<output save file location>");
		}
	}
	
	private static boolean isValidInteger(String string) {
		if (string.length() >= 2 && string.length() <= 10 && string.charAt(0) == '-') {
			for (int i = 1; i < string.length(); i++) {
				if (string.charAt(i) < '0' || string.charAt(i) > '9') {
					return false;
				}
			}
			return true;
		} else if (string.length() >= 1 && string.length() <= 9) {
			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(i) < '0' || string.charAt(i) > '9') {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
