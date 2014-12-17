package ide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private static String getMontador() {
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (diretorio.getName().endsWith(".jar")) {
            return diretorio.getParent() + "/ide/recursos/dasm.exe";
        }
        return Montador.class.getResource("recursos/dasm.exe").getFile();
    }

    private static String getInclude() {
        File diretorio = new File(Recursos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (diretorio.getName().endsWith(".jar")) {
            return diretorio.getParent() + "/ide/recursos/includes";
        }
        return Montador.class.getResource("recursos/includes").getFile().substring(1);
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

    public static void iniciar() {

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
