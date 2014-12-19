/*
 * Copyright 2014 Denison.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Denison
 */
public class Projeto {

    private final String nome;
    private Boolean salvo;
    private final File diretorio;
    private final File sourceFile;
    private final File romFile;

    /**
     * Cria um objeto de um projeto existente
     *
     * @param diretorio
     */
    public Projeto(File diretorio) {
        if (diretorio != null && diretorio.isDirectory() && diretorio.exists()) {
            nome = diretorio.getName();
            this.diretorio = diretorio;
            sourceFile = new File(diretorio, nome + ".asm");
            romFile = new File(diretorio, nome + ".bin");
            salvo = true;
        } else if (diretorio != null && diretorio.exists()) {
            nome = diretorio.getName();
            this.diretorio = diretorio;
            sourceFile = diretorio;
            romFile = null;
            salvo = true;
        } else {
            throw new IllegalArgumentException("Can't create a project");
        }
    }

    public Projeto(String nome, boolean createHelloSource) {
        this.nome = nome;
        diretorio = new File(Recursos.PROJECTS_DIR, nome);
        diretorio.mkdir();
        sourceFile = new File(diretorio, nome + ".asm");
        romFile = new File(diretorio, nome + ".bin");
        salvo = true;
        if (createHelloSource) {
            save(Recursos.getHelloSource(nome));
        } else {
            try {
                sourceFile.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        System.out.println("created project: " + nome);
    }

    public String getNome() {
        return nome;
    }

    public Boolean getSalvo() {
        return salvo;
    }

    public File getDiretorio() {
        return diretorio;
    }

    public void setSalvo(Boolean salvo) {
        this.salvo = salvo;
    }

    @Override
    public String toString() {
        if (salvo) {
            return nome;
        } else {
            return nome + " *";
        }
    }

    public String getSource() {
        try {
            FileReader fileInput = new FileReader(sourceFile);
            BufferedReader leitor = new BufferedReader(fileInput);
            StringBuilder buffer = new StringBuilder();
            String linha = leitor.readLine();
            while (linha != null) {
                buffer.append(linha);
                buffer.append('\n');
                linha = leitor.readLine();
            }
            if (buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
            return buffer.toString();
        } catch (IOException ex) {
            return "";
        }
    }

    public String getSourceFile() {
        return sourceFile.getAbsolutePath();
    }

    public File getRomFile() {
        return romFile;
    }

    public void save(String conteudo) {
        FileWriter arquivoFonte;
        try {
            arquivoFonte = new FileWriter(sourceFile);
            arquivoFonte.write(conteudo); //grava no arquivo o codigo-fonte
            arquivoFonte.close();
            System.out.println("saved file: " + sourceFile.getName());
            salvo = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void delete() {
        if (isFile()) {
            sourceFile.delete();
            System.out.println("deleted file: " + nome);
        } else {
            for (File file : diretorio.listFiles()) {
                file.delete();
            }
            diretorio.delete();
            System.out.println("deleted project: " + nome);
        }
    }

    public boolean isFile() {
        return (romFile == null);
    }
}