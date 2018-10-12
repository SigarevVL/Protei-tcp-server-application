package ru.protei.client.menu;

import java.util.Scanner;

public class SimpleMenu {
    private static final String MAIN_MENU = "To select a command, type one of them:\n" +
            "- select \n- select_by_mask \n- insert \n- update \n- delete\n" +
            "Type \"exit\" if you want to close the application";

    public String[] launchMenu() {
        String[] strings = new String[2];
        Scanner scanner = new Scanner(System.in);
        boolean open = true;

        do {
            System.out.println(MAIN_MENU);
            String selectedCommand = scanner.nextLine();
            if (!selectedCommand.equals("exit")) {

                System.out.println("Type \"<-\" if you want to go back into the main menu\nOR");
                switch (selectedCommand) {
                    case "select":
                        System.out.println("Enter the word (for example: home):");
                        break;

                    case "select_by_mask":
                        System.out.println("Enter the mask where \"_\" one any character\n" +
                                "\"%\" any number of any characters\n" +
                                "(for example: %n_ ):");
                        break;

                    case "insert":
                        System.out.println("Enter the word and explanation separated by \"=\"\n" +
                                "(for example: home=place where we live):");
                        break;

                    case "update":
                        System.out.println("Enter the word and explanation separated by \"=\"\n" +
                                "(for example: home=place where we live):");
                        break;

                    case "delete":
                        System.out.println("Enter the word (for example: home):");
                        break;
                }

                String commandParameter = scanner.nextLine();
                if (commandParameter.equals("<-")) {
                } else {
                    open = false;
                    strings[0] = selectedCommand;
                    strings[1] = commandParameter;
                }

            } else {
                strings[0] = selectedCommand;
                strings[1] = "";
                open = false;
            }

        } while (open);
        return strings;
    }
}
