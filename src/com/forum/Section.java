package com.forum;

import java.io.*;
import java.util.LinkedList;

public class Section implements Serializable {
    private String sectionname;
    private LinkedList<DiscussionThread> threads=new LinkedList<DiscussionThread>();

    public Section(String name) {
        this.sectionname=name;
    }

    public String getSectionname() {
        return sectionname;
    }

    public LinkedList<DiscussionThread> getThreads() {
        return threads;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }

    public void printSection(PrintWriter output) {
        output.println("Przeglądasz sekcję: "+sectionname+"\n");
        output.flush();
        output.println("Dostępne tematy:");
        output.flush();
        if(threads.isEmpty()) {
            output.println("W tym dziale nie ma tematów");
            output.flush();
        }
        else {
            int i=1;
            for(DiscussionThread thread: threads) {
                output.println(i + ". " + thread.getTopic());
                output.flush();
                i++;
            }
        }
    }

    public void runSection(PrintWriter output,BufferedReader in, String flag) throws IOException{

            String command = "";
            String error = "";

            while (!command.equals("Powrot")) {
                output.println("\n\n==============================================");
                output.flush();
                output.println("Dostępne komendy: Wybierz, Dodaj, Usun, Powrot");
                output.flush();
                output.println("==============================================\n");
                output.flush();
                this.printSection(output);
                output.println(error);
                output.flush();
                output.println(flag);
                output.flush();
                error = "";
                command=in.readLine();
                switch (command) {
                    case "Wybierz":
                        output.println("Podaj numer tematu, do którego chcesz przejść: ");
                        output.flush();
                        output.println(flag);
                        output.flush();
                        command=in.readLine();
                        try {
                            int number = Integer.parseInt(command);
                            this.getThreads().get(number-1).runDiscussionThread(output, in, flag);
                        }
                        catch(NumberFormatException e) {
                            error="Podana wartość nie jest numerem";
                        }
                        catch(IndexOutOfBoundsException e) {
                            error="Nie ma tematu o podanym numerze";
                        }
                        break;
                    case "Dodaj":
                        output.println("Podaj nazwę nowego tematu: ");
                        output.flush();
                        output.println(flag);
                        output.flush();
                        String topic = in.readLine();
                        if(topic.length()<3) {
                            error="Nazwa tematu musi składać się przynajmniej z 3 znaków";
                            break;
                        }
                        output.println("Podaj swój pseudonim: ");
                        output.flush();
                        output.println(flag);
                        output.flush();
                        String nickname = in.readLine();
                        if(nickname.length()<3) {
                            error="Twój pseudonim musi składać się przynajmniej z 3 znaków";
                            break;
                        }
                        output.println("Wpisz treść tematu");
                        output.flush();
                        output.println(flag);
                        output.flush();
                        String message = in.readLine();
                        if(message.length()<1) {
                            error="Treść tematu nie może być pusta";
                            break;
                        }
                        threads.add(new DiscussionThread(topic, (new Post(nickname, message))));
                        this.getThreads().getLast().runDiscussionThread(output, in, flag);
                        break;
                    case "Usun":
                        output.println("Podaj numer sekcji, którą chcesz usunąć: ");
                        output.flush();
                        command=in.readLine();
                        try {
                            int number = Integer.parseInt(command);

                            output.println("Czy na pewno chcesz usunąć temat \""+this.getThreads().get(number-1).getTopic()+"\'? Jeśli chcesz wpisz \"Tak\" ");
                            output.flush();
                            command=in.readLine();
                            if(command.equals("Tak")) this.getThreads().remove(number-1);
                        }
                        catch(NumberFormatException e) {
                            error="Podana wartość nie jest numerem";
                        }
                        catch(IndexOutOfBoundsException e) {
                            error="Nie ma tematu o podanym numerze";
                        }
                        break;
                }
        }
    }
}
