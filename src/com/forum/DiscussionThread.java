package com.forum;

import java.io.*;
import java.util.LinkedList;

public class DiscussionThread implements Serializable {
    private String topic;
    private LinkedList<Post> posts=new LinkedList<Post>();

    public DiscussionThread(String topic, Post post){
        this.topic=topic;
        this.posts.add(post);
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void printThread(PrintWriter output) {
        output.println("Przeglądasz temat: "+topic+"\n");
        output.flush();
        int i=1;
        for(Post post: posts) {
            output.println(i+". ["+post.getAuthor()+"]");
            output.flush();
            output.println(post.getMessage()+"\n");
            output.flush();
            i++;
        }
    }


    public void runDiscussionThread(PrintWriter output,BufferedReader in, String flag) throws IOException {

        String command = "";
        String error = "";

        while (!command.equals("Powrot")) {
            output.println("\n\n====================================");
            output.flush();
            output.println("Dostępne komendy: Odpowiedz, Powrot");
            output.flush();
            output.println("====================================\n");
            output.flush();
            this.printThread(output);
            output.println(error);
            output.flush();
            output.println(flag);
            output.flush();
            error = "";
            command=in.readLine();
            switch (command) {
                case "Odpowiedz":
                    output.println("Podaj swój pseudonim: ");
                    output.flush();
                    output.println(flag);
                    output.flush();
                    String nickname = in.readLine();
                    if(nickname.length()<3) {
                        error = "Twój pseudonim musi składać się przynajmniej z 3 znaków";
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
                    addPost(new Post(nickname,message));
            }
        }
    }
}
