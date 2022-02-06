package main;

import java.io.*;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("String 1");
        hashSet.add("String 2");
        AmigoSet<String> amigoSet1 = new AmigoSet<>(hashSet);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(amigoSet1);
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        AmigoSet<String> amigoSet2 = (AmigoSet<String>) ois.readObject();
        System.out.println("Before serialization:\n" + amigoSet1 + "\n");
        System.out.println("After deserialization:\n" + amigoSet2 + "\n");
        System.out.println("Are sets equal? - " + amigoSet1.equals(amigoSet2));
    }
}