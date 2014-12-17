/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Denison
 */
public class Montador {

    public static String getCreateBIN(Projeto proj) {
        Process process;
        String source = proj.getSourceFile();
        String destino = proj.getDiretorio().getAbsolutePath()+"/"+proj.getNome();
        String saida = "";
        try {
            process = new ProcessBuilder(Recursos.MONTADOR, source,"-o"+destino+".bin", "-f3", "-I"+Recursos.INCLUDE+"/").start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            while ((line = br.readLine()) != null) {
                saida += line+"\n";
            }
        } catch (IOException ex) {
            return ex.getMessage();
        }
        return saida;
    }
}
