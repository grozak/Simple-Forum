package com.forum;


import java.io.*;
import java.util.LinkedList;

public class Forum implements Serializable{
    private String forumname;
    private LinkedList<Section> sections=new LinkedList<Section>();

    public Forum(String name) {
        this.forumname=name;
    }

    public LinkedList<Section> getSections() {
        return sections;
    }

    private void addSection(Section section){
        sections.add(section);

    }

    public void printForum(PrintWriter output) throws IOException{
        output.println(this.forumname+"\n");
        output.flush();
        output.println("Dostępne działy:");
        output.flush();
        if(sections.isEmpty()) {
            output.println("Brak działów");
            output.flush();
        }
        else {
            int i = 1;
            for (Section section : this.sections) {
                output.println(i + ". " + section.getSectionname());
                output.flush();
                i++;
            }
        }
    }

    public void runForum(PrintWriter output,BufferedReader in, String flag) throws IOException{

        String command="";
        String error="";
        while(!command.equals("Koniec")) {
            output.println("\n\n=====================================================");
            output.flush();
            output.println("Dostępne komendy: Wybierz, Dodaj, Usun, Nazwa, Koniec");
            output.flush();
            output.println("=====================================================\n");
            output.flush();
            this.printForum(output);
            output.println(error);
            output.flush();
            output.println(flag);
            output.flush();

            error="";
            command=in.readLine();
            switch (command) {
                case "Wybierz":
                    output.println("Podaj numer działu, do którego chcesz przejść: ");
                    output.flush();
                    output.println(flag);
                    output.flush();
                    command=in.readLine();
                    try {
                        int number = Integer.parseInt(command);
                        this.getSections().get(number-1).runSection(output, in, flag);
                    }
                    catch(NumberFormatException e) {
                        error="Podana wartość nie jest numerem";
                    }
                    catch(IndexOutOfBoundsException e) {
                        error="Nie ma działu o podanym numerze";
                    }
                    break;

                case "Dodaj":
                    String message;
                    output.println("Podaj nazwę nowej sekcji: ");
                    output.flush();
                    output.println(flag);
                    output.flush();
                    message = in.readLine();
                    if(message.length()>0) {
                        this.addSection(new Section(message));
                    }
                    else error="Nazwa nie może być pusta";
                    break;
                case "Usun":
                    output.println("Podaj numer sekcji, którą chcesz usunąć: ");
                    output.flush();
                    output.println(flag);
                    output.flush();
                    command=in.readLine();
                    try {
                        int number = Integer.parseInt(command);

                        output.println("Czy na pewno chcesz usunąć sekcję \""+this.getSections().get(number-1).getSectionname()+"\"? Spowoduje to " +
                                "usunięcie wszystkich tematów znajdujących się wewnątrz. Jeśli chcesz wpisz \"Tak\" ");
                        output.flush();
                        output.println(flag);
                        output.flush();
                        command=in.readLine();
                        if(command.equals("Tak")) this.getSections().remove(number-1);
                    }
                    catch(NumberFormatException e) {
                        error="Podana wartość nie jest numerem";
                    }
                    catch(IndexOutOfBoundsException e) {
                        error="Nie ma tematu o podanym numerze";
                    }
                    break;
                case "Nazwa":
                    output.println("Podaj nową nazwę forum: ");
                    output.flush();
                    output.println(flag);
                    output.flush();
                    command= in.readLine();
                    if(command.length()>0) {
                        this.forumname = command;
                    }
                    else error="Nazwa nie może być pusta";
                    break;

            }
        }
    }

    public void save(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            oos.close();
            fos.close();
        }
        catch(IOException e) {
            System.out.println("Błąd operacji zapisu");
        }
    }
}


