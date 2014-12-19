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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Denison
 */
public class Recursos {

    public static final ImageIcon LOGO = new ImageIcon(Recursos.class.getResource("gui/imgs/logo.png"));

    public static File PROJECTS_DIR;

    public static Icon ICON;

    public static byte[] ROM;

    public static String MONTADOR;

    public static String INCLUDE;
    
    public static URL HTML;

    public Recursos() {
        if (PROJECTS_DIR == null) {
            PROJECTS_DIR = newDir();
            HTML = Recursos.class.getResource("recursos/welcome.html");
            ICON = new ImageIcon(Recursos.class.getResource("gui/imgs/icon_project.png"));
            ROM = getDefaultROM();
            MONTADOR = getMontador();
            INCLUDE = getInclude();
        }
    }
    
    public static String assembler(Projeto proj) {
        Process process;
        String source = proj.getSourceFile();
        String destino = proj.getDiretorio().getAbsolutePath()+"/"+proj.getNome();
        String saida = "";
        try {
            process = new ProcessBuilder(MONTADOR, source,"-o"+destino+".bin", "-f3", "-I"+INCLUDE+"/", "-I"+proj.getDiretorio()+"/").start();
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

    private static String getMontador() {
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (diretorio.getName().endsWith(".jar")) {
            return diretorio.getParent() + "/ide/recursos/dasm.exe";
        }
        return Recursos.class.getResource("recursos/dasm.exe").getFile();
    }

    private static String getInclude() {
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (diretorio.getName().endsWith(".jar")) {
            return diretorio.getParent() + "/ide/recursos/includes";
        }
        return Recursos.class.getResource("recursos/includes").getFile().substring(1);
    }

    private static byte[] getDefaultROM() {
        byte[] romBytes = new byte[2048];
        File aROMFile = null;
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (diretorio.getName().endsWith(".jar")) {
            aROMFile = new File(diretorio.getParent() + "/ide/recursos");
            if (!aROMFile.exists()) {
                try {
                    extrairDiretorioJar(diretorio, "recursos");
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
            aROMFile = new File(aROMFile, "ide.bin");
        } else {
            try {
                aROMFile = new File(ide.AtariIDE.class.getResource("recursos/ide.bin").toURI());
            } catch (URISyntaxException ex) {
                System.err.println(ex);
            }
        }
        try {
            try (FileInputStream zFIS = new FileInputStream(aROMFile)) {
                zFIS.read(romBytes, 0, romBytes.length);
                zFIS.close();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return romBytes;
    }

    private static File newDir() {
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (!diretorio.getName().endsWith(".jar")) {
            diretorio = new File("Projects");
        } else {
            diretorio = new File(diretorio.getParent() + "/Projects");
        }
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        return diretorio;
    }

    public static String getHelloSource(String nome) {
        return ";\n"
                + "; " + nome + ".asm\n"
                + ";\n"
                + ";   dasm " + nome + ".asm -ohello.bin -f3\n"
                + ";\n"
                + "    PROCESSOR 6502\n"
                + "    INCLUDE \"vcs.h\"\n"
                + "    include \"macro.h\"\n"
                + "\n"
                + "    ORG $F800       ; Start\n"
                + "\n"
                + "Reset:\n"
                + "    SEI\n"
                + "    CLD\n"
                + "    LDX #$FF\n"
                + "    TXS\n"
                + "    LDA #0\n"
                + "Clear_Mem:\n"
                + "    STA 0,X\n"
                + "    DEX\n"
                + "    BNE Clear_Mem\n"
                + "\n"
                + "    LDA #$00\n"
                + "    STA COLUBK      ; Set Background to Black\n"
                + "    LDA #$FF\n"
                + "    STA COLUPF\n"
                + ";///////////////////  Picture Starts Here /////////////////////////////\n"
                + "Start_Frame:\n"
                + "; Start VSYNC\n"
                + "    LDA #2\n"
                + "    STA VSYNC\n"
                + "    STA WSYNC \n"
                + "    STA WSYNC\n"
                + "    STA WSYNC       ; 3 Scanlines of VSYNC\n"
                + "    LDA #0\n"
                + "    STA VSYNC\n"
                + "; End VSYNC\n"
                + "    LDX #37\n"
                + "Vertical_Blank:\n"
                + "    STA WSYNC\n"
                + "    DEX\n"
                + "    BNE Vertical_Blank\n"
                + "    \n"
                + "    LDA #0\n"
                + "    STA VBLANK    \n"
                + ";////////////// Start To Draw Playfield ///////////////////////////////\n"
                + "    LDX #0\n"
                + "Draw_Picture\n"
                + "        LDA Screen_PF0,X\n"
                + "        STA PF0\n"
                + "        LDA Screen_PF1,X\n"
                + "        STA PF1\n"
                + "        LDA Screen_PF2,X\n"
                + "        STA PF2\n"
                + "        SLEEP 4\n"
                + "        LDA Screen_PF3,X\n"
                + "        STA PF0\n"
                + "        LDA Screen_PF4,X\n"
                + "        STA PF1\n"
                + "        LDA Screen_PF5,X\n"
                + "        STA PF2\n"
                + "        STA WSYNC\n"
                + "        INX\n"
                + "        CPX #9             ; End of text size\n"
                + "        BCC Draw_Picture\n"
                + ";////////////// End Of Display ////////////////////////////////////////\n"
                + "    LDA #%01000010         ; Disable VIA Output\n"
                + "    STA VBLANK\n"
                + "    ; 30 scanlines of overscan...\n"
                + "    LDX #30\n"
                + "Overscan:\n"
                + "    STA WSYNC\n"
                + "    DEX\n"
                + "    BNE Overscan\n"
                + "; Build Next Frame\n"
                + "    JMP Start_Frame    \n"
                + "; Text:\n"
                + "Screen_PF0:\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000;<--\n"
                + "    .byte #%11100000; H\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "Screen_PF1:\n"
                + "    .byte #%01101001\n"
                + "    .byte #%01001001\n"
                + "    .byte #%01001001\n"
                + "    .byte #%01001001;-->\n"
                + "    .byte #%01101001;ell\n"
                + "    .byte #%01001001\n"
                + "    .byte #%01001001\n"
                + "    .byte #%01001001\n"
                + "    .byte #%01101101\n"
                + "Screen_PF2:\n"
                + "    .byte #%10011100\n"
                + "    .byte #%10010100\n"
                + "    .byte #%10010100\n"
                + "    .byte #%10010100;<--\n"
                + "    .byte #%10010100;o\n"
                + "    .byte #%10010100\n"
                + "    .byte #%10010100\n"
                + "    .byte #%10010100\n"
                + "    .byte #%00011101\n"
                + "Screen_PF3:\n"
                + "    .byte #%10000000\n"
                + "    .byte #%10000000\n"
                + "    .byte #%10000000\n"
                + "    .byte #%10000000;<--\n"
                + "    .byte #%10100000;w\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%10100000\n"
                + "    .byte #%01010000\n"
                + "Screen_PF4:\n"
                + "    .byte #%01110110\n"
                + "    .byte #%01010101\n"
                + "    .byte #%01010101\n"
                + "    .byte #%01010101;-->\n"
                + "    .byte #%01010110;or\n"
                + "    .byte #%01010101\n"
                + "    .byte #%01010101\n"
                + "    .byte #%01010101\n"
                + "    .byte #%01110101\n"
                + "Screen_PF5:\n"
                + "    .byte #%00110010\n"
                + "    .byte #%01010010\n"
                + "    .byte #%01010010\n"
                + "    .byte #%01010010;<--\n"
                + "    .byte #%01010010;ld\n"
                + "    .byte #%01010010\n"
                + "    .byte #%01010010\n"
                + "    .byte #%01010010\n"
                + "    .byte #%00110110\n"
                + "\n"
                + "; Enable TIA Output\n"
                + "    ORG $FFFA       ; Config:\n"
                + "    .WORD Reset     ;     NMI\n"
                + "    .WORD Reset     ;     RESET\n"
                + "    .WORD Reset     ;     IRQ\n"
                + "   END";
    }

    /**
     * extrai arquivos que são necessarios fora do jar
     */
    private static void extrairDiretorioJar(File arquivoJar, String diretorio) throws ZipException, IOException {
        ZipFile jar = null;
        File arquivo;
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer = new byte[2048]; // 2 Kb //TAMANHO_BUFFER
        try {
            jar = new JarFile(arquivoJar);
            Enumeration e = jar.entries();
            while (e.hasMoreElements()) {
                ZipEntry entrada = (JarEntry) e.nextElement();
                if (entrada.getName().contains(diretorio)) {
                    arquivo = new File(entrada.getName());
                    //se for diretório inexistente, cria a estrutura
                    //e pula pra próxima entrada
                    if (entrada.isDirectory() && !arquivo.exists()) {
                        arquivo.mkdirs();
                        continue;
                    }
                    //se a estrutura de diretórios não existe, cria
                    if (!arquivo.getParentFile().exists()) {
                        arquivo.getParentFile().mkdirs();
                    }
                    try {
                        //lê o arquivo do zip e grava em disco
                        is = jar.getInputStream(entrada);
                        os = new FileOutputStream(arquivo);
                        int bytesLidos;
                        if (is == null) {
                            throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
                        }
                        while ((bytesLidos = is.read(buffer)) > 0) {
                            os.write(buffer, 0, bytesLidos);
                        }
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception ex) {
                            }
                        }
                        if (os != null) {
                            try {
                                os.close();
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
