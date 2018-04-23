package com.forum;

import java.io.*;

class Reader {
    Forum open(String path) throws IOException, ClassNotFoundException{
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fis);

            Forum temp = (Forum) ois.readObject();
            ois.close();
            fis.close();
            return temp;
        } catch(FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku. Utworzono nowe forum");
            Forum forum=new Forum("Nowe forum");
            return forum;
        } catch(IOException e) {
            System.out.println("Błąd wczytywania. Utworzono nowe forum");
            Forum forum=new Forum("Nowe forum");
            return forum;
        }
    }
}