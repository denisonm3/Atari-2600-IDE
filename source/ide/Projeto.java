/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import ide.gui.ProjectsList;
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
            this.diretorio = diretorio;
            nome = diretorio.getName();
            sourceFile = new File(diretorio, nome + ".asm");
            romFile = new File(diretorio, nome + ".bin");
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
            createHelloSource();
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
            System.out.println("saved file: " + nome + ".asm");
            salvo = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void createHelloSource() {
        String source = ";\n"
                + "; "+nome+".asm\n"
                + ";\n"
                + ";   dasm "+nome+".asm -ohello.bin -f3\n"
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
        save(source);
    }

    public void delete() {
        sourceFile.delete();
        romFile.delete();
        diretorio.delete();
        System.out.println("deleted project: " + nome);
    }
}
