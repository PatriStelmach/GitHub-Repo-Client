package com.example.demo.Services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

import static com.example.demo.Services.GitService.definePath;
import static com.example.demo.Services.GitService.estAppId;

@Service
public class CredentialsService
{
    //switch in while in case someone wants to enter his key path instead of making it env var
    public static void configurePrivateKeyPath()
    {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput)
        {
            System.out.println("\nDo you want to enter your custom path or use the environmental variable '$GITHUB_PRIVATE_KEY_PATH' ?\n" +
                    "Press y/n (yes/no): ");
            String inputPath = scanner.nextLine().trim().toLowerCase();

            switch (inputPath)
            {
                case "y":
                    while (true)
                    {
                        System.out.println("Define the path to your PKC1 GitHub key: ");
                        inputPath = scanner.nextLine();

                        if (inputPath.matches(".*[<>\"|?*].*"))
                        {
                            System.out.println("The path contains not allowed characters or this is not a path to file \n");
                            continue;
                        }

                        try
                        {
                            definePath(inputPath);
                            validInput = true;
                            break;
                        } catch (Exception e) {
                            System.out.println("The path contains not allowed characters or this is not a path to file \n");
                        }
                    }
                    break;

                case "n":
                    validInput = true;
                    break;

                default:
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    break;
            }
        }
    }

    //switch in while in case someone wants to enter his APP ID instead of making it env var
    public static void configureAppId()
    {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        System.out.println("\nDo you want to enter your APP_ID manually or use the environmental variable '$GITHUB_APP_ID' ?\n" +
                "Press y/n (yes/no): ");

        while (!validInput)
        {
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice)
            {
                case "y":
                    while (true)
                    {
                        System.out.println("Enter your GitHub App ID: ");
                        if (!scanner.hasNextLong())
                        {
                            System.out.println("App ID must be a number.\nTry again.");
                            scanner.nextLine();
                        }
                        else
                        {
                            try
                            {
                                Long appId = scanner.nextLong();
                                estAppId(appId);
                                validInput = true;
                                break;
                            } catch (Exception e) {
                                System.out.println("Wrong App ID. Try again.");
                                scanner.nextLine();
                            }
                        }
                    }
                    break;

                case "n":
                    validInput = true;
                    break;

                default:
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    break;
            }
        }
    }
}
