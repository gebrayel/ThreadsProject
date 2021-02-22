package Classes;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import javax.swing.JOptionPane;

public class Functions {

    public Functions() {

    }

    public String[] readTXT() {
        String line;
        String text = "";
        File file = new File("test\\data.csv");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    if (!line.isEmpty()) {
                        text += line + "\n";
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al leer el archivo");
        }
        return text.split("\n");
    }

    public String[] separateData(String[] text) {
        if (text.length != 1) {
            String[] data = new String[text.length - 5];
            int j = 0;
            for (int i = 0; i < text.length; i++) {
                String[] line = text[i].split(",");
                if (line.length != 1) {
                    data[j] = line[1];
                    j++;
                }
            }
            return data;
        }
        return text;
    }

}
