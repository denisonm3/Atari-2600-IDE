package ide.gui.code;

import ide.gui.code.Opcodes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;

/**
 * Estilo de texto para visualizar código Assembly 6502 Após ser instanciado
 * deve chamar o método: configurarTextComponent(JTextComponent) Com esse método
 * o componente de texto será configurado
 *
 * @author Denison
 */
public class DocumentAssembly6502 extends DefaultStyledDocument implements CaretListener {

    private final String[] keyStrings = {
        "\"(.*)\"",
        "(\'.\')",
        "(\'..\')"};
    private final String keyBinary = "\\b[0-1]{8}\\b";
    private final String keyNumbers = "\\b[0-9]+\\b";
    private final MutableAttributeSet style;
    private final Color defaultStyle = Color.black;
    private final Color commentStyle = Color.lightGray;
    private final Color OpcodeStyle = Color.blue;
    private final Color RegisterStyle = new Color(250, 125, 0);
    private final Color numberStyle = new Color(0, 150, 0);
    private final Color stringStyle = Color.cyan;
    private final Pattern singleCommentDelim = Pattern.compile(";(.*)\n");
    private final Font font;
    //Desenha linhas
    private final JTextArea BarraNumLinhas;
    private final JLabel BarraPosCursor;
    private Integer numeroLinhas;
    //Popup para auto completar
    private JPopupMenu popup;
    private JList ListAutoCompletar;
    //Controle desfazer e refazer
    private UndoManager undoManager;

    public DocumentAssembly6502() {
        putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
        style = new SimpleAttributeSet();
        //Define estilo de fonte
        font = new Font(Font.MONOSPACED, Font.BOLD, 12);
        StyleConstants.setFontFamily(style, Font.MONOSPACED);
        StyleConstants.setBold(style, true);
        StyleConstants.setFontSize(style, 12);
        //Desenha linhas
        numeroLinhas = 1;
        BarraNumLinhas = new JTextArea();
        BarraNumLinhas.setDisabledTextColor(Color.BLACK);
        BarraNumLinhas.setEnabled(false);
        BarraNumLinhas.setMargin(new Insets(-2, 0, 0, 0));
        BarraNumLinhas.setColumns(1);
        BarraNumLinhas.setFont(font);
        BarraNumLinhas.setText("1");
        BarraNumLinhas.setBackground(Color.lightGray);
        //Indicar posição do curso(teclado)
        BarraPosCursor = new JLabel("Linha: 0 | Coluna: 0 ");
        BarraPosCursor.setBackground(Color.lightGray);
        BarraPosCursor.setHorizontalAlignment(SwingConstants.RIGHT);
        //auto completar
        criarPopup();
        criarUndo();
    }

    public Font getFont() {
        return font;
    }

    public Component getLinhas() {
        return BarraNumLinhas;
    }

    public Component getCursor() {
        return BarraPosCursor;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str.length() == 1) {
            switch (str) {
                case "\n":
                    String text = getText(0, offset);
                    int ultimaQuebra = text.lastIndexOf("\n");
                    ultimaQuebra++;
                    String espacos = "\n";
                    while (ultimaQuebra < text.length() && text.charAt(ultimaQuebra) == ' ') {
                        ultimaQuebra++;
                        espacos += " ";
                    }
                    super.insertString(offset, espacos, style);
                case "\t":
                    super.insertString(offset, "    ", style);
                    break;
                default:
                    super.insertString(offset, str, style);
                    break;
            }
            processChangedLines(offset, str.length());
        } else {
            //Substituir tabs
            str = str.replaceAll("\t", "    ");
            //insere modificações
            Pattern p = Pattern.compile("\n");
            Matcher m = p.matcher(str);
            int total = 0;
            while (m.find()) {
                total++;
            }
            inserirLinhas(total);
            super.insertString(offset, str, attr);
            processChangedLines(offset, str.length());
        }
    }

    @Override
    public void remove(int offset, int length) throws BadLocationException {
        Pattern p = Pattern.compile("\n");
        Matcher m = p.matcher(getText(offset, length));
        int total = 0;
        while (m.find()) {
            total++;
        }
        if (total > 0) {
            numeroLinhas -= total;
            for (int i = 0; i < total; i++) {
                int fim = BarraNumLinhas.getText().length();
                int pos = BarraNumLinhas.getText().lastIndexOf('\n', fim);
                BarraNumLinhas.getDocument().remove(pos, fim - pos);
            }
            if (numeroLinhas.toString().length() != BarraNumLinhas.getColumns()) {
                BarraNumLinhas.setColumns(numeroLinhas.toString().length());
            }
        }
        super.remove(offset, length);
        processChangedLines(offset, length);
    }

    public void processChangedLines(int offset, int length) throws BadLocationException {
        // Normal Text
        Element defaultElement = getDefaultRootElement();
        int line = defaultElement.getElementIndex(offset);
        int lineend = defaultElement.getElementIndex(offset + length);
        int start = defaultElement.getElement(line).getStartOffset();
        int end = defaultElement.getElement(lineend).getEndOffset();

        String text = getText(start, end - start);
        StyleConstants.setForeground(style, defaultStyle);
        StyleConstants.setBold(style, false);
        //StyleConstants.setItalic(style, false);
        setCharacterAttributes(start, end - start, style, true);

        // OPCODE
        StyleConstants.setBold(style, true);
        StyleConstants.setForeground(style, OpcodeStyle);
        for (String keyword : Opcodes.OPCODES_NOME) {
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(text);

            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
            }
        }

        // REGISTER
        StyleConstants.setBold(style, true);
        StyleConstants.setForeground(style, RegisterStyle);
        for (String keyword : RegisterNames.REGISTER_NOME) {
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(text);

            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
            }
        }

        //numbers
        StyleConstants.setForeground(style, numberStyle);
        {
            Pattern p = Pattern.compile(keyNumbers);
            Matcher m = p.matcher(text);
            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
            }
        }

        //String
        StyleConstants.setForeground(style, stringStyle);
        for (String keyword : keyStrings) {
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(text);

            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
            }
        }

        //binary
        StyleConstants.setForeground(style, Color.BLACK);
        {
            Pattern p = Pattern.compile(keyBinary);
            Matcher m = p.matcher(text);

            while (m.find()) {
                StyleConstants.setBackground(style, Color.CYAN);
                setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
                StyleConstants.setBackground(style, Color.BLACK);
                String temp = m.group();
                for (int i = 0; i < 8; i++) {
                    if (temp.charAt(i) == '1') {
                        setCharacterAttributes(start + m.start() + i, 1, style, true);
                    }
                }
            }
        }
        StyleConstants.setBackground(style, Color.WHITE);

        // Comments
        StyleConstants.setForeground(style, commentStyle);
        Matcher m = singleCommentDelim.matcher(text);
        while (m.find()) {
            //int line1 = rootElement.getElementIndex(slc.start());
            //int endOffset = rootElement.getElement(line1).getEndOffset() - 1;
            setCharacterAttributes(start + m.start(), m.end() - m.start(), style, true);
        }
    }

    private void inserirLinhas(int total) throws BadLocationException {
        Document doc = BarraNumLinhas.getDocument();
        for (int i = 0; i < total; i++) {
            numeroLinhas++;
            doc.insertString(doc.getLength(), "\n" + numeroLinhas, null);
        }
        if (numeroLinhas.toString().length() != BarraNumLinhas.getColumns()) {
            BarraNumLinhas.setColumns(numeroLinhas.toString().length());
        }
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        //System.out.println("Mover");
        int inicio = ce.getDot();
        int fim = ce.getMark();
        JTextComponent jTexto = (JTextComponent) ce.getSource();

        if (inicio == fim) {// no selection
            try {
                Rectangle caretCoords = jTexto.modelToView(inicio);
                BarraPosCursor.setText("Linha: " + ((caretCoords.y) / 17 + 1) + " | Coluna: " + (caretCoords.x - 6) / 7 + " ");
            } catch (Exception ex) {
                //Logger.getLogger(DocumentColor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (inicio > fim) {
                inicio = ce.getMark();
                fim = ce.getDot();
            }
            if (fim - inicio > 1 && fim - inicio < 50) {
                try {
                    //Obtem o texto selecionado
                    String texto = this.getText(inicio - 1, fim - inicio + 2);
                    System.out.println(">"+texto+"<");
                    char start = texto.charAt(0);
                    char end = texto.charAt(texto.length() - 1);
                    texto = texto.substring(1, texto.length() - 1);
                    System.out.println(">"+texto+"<");
                    System.out.println("("+start+")");
                    System.out.println("("+end+")");
                    if ((start == ' ' || start == '\n')
                            && (end == ' ' || end == '\n' || end == ':')
                            && !texto.contains(" ")
                            && !texto.contains("\n")) {
                        texto = "\\b" + texto + "\\b";
                        processChangedLines(0, getLength());
                        //Marcar texto igual ao texto selecionado
                        StyleConstants.setForeground(style, Color.BLACK);
                        StyleConstants.setBackground(style, Color.YELLOW);
                        Pattern p = Pattern.compile(texto);
                        Matcher m = p.matcher(jTexto.getText());
                        while (m.find()) {
                            setCharacterAttributes(m.start(), m.end() - m.start(), style, true);
                        }
                        StyleConstants.setBackground(style, Color.WHITE);
                    }
                } catch (Exception ex) {
                    StyleConstants.setBackground(style, Color.WHITE);
                }
            }
            BarraPosCursor.setText("selection from: " + inicio + " to " + fim + " ");
        }
    }

    public void close() {
        numeroLinhas = 1;
        BarraNumLinhas.setText("1");
        BarraPosCursor.setText("Linha: 0 | Coluna: 0 ");
    }

    private void criarPopup() {
        popup = new JPopupMenu();
        String[] itens = new String[Opcodes.OPCODES_DESCRICAO.length + RegisterNames.REGISTER_DESCRICAO.length];
        System.arraycopy(Opcodes.OPCODES_DESCRICAO, 0, itens, 0, Opcodes.OPCODES_DESCRICAO.length);
        System.arraycopy(RegisterNames.REGISTER_DESCRICAO, 0, itens, Opcodes.OPCODES_DESCRICAO.length, RegisterNames.REGISTER_DESCRICAO.length);
        ListAutoCompletar = new JList(itens);
        ListAutoCompletar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListAutoCompletar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    inserirAutoCompletar();
                }
            }
        });

        ListAutoCompletar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    inserirAutoCompletar();
                }
            }
        });

        JScrollPane popupBarra = new JScrollPane();
        popupBarra.setBorder(null);
        popupBarra.setViewportView(ListAutoCompletar);
        popup.add(popupBarra);
    }

    private void inserirAutoCompletar() {
        try {
            JTextComponent jTextPane = (JTextComponent) popup.getInvoker();
            int dot = jTextPane.getCaret().getDot();
            String inserir = ListAutoCompletar.getSelectedValue().toString().substring(0, 4);
            if (dot > 0) {
                String text = getText(dot - 2, 2);
                if (text.charAt(0) == ' ' && text.charAt(1) == inserir.charAt(0)) {
                    dot--;
                    remove(dot, 1);
                }
            }
            insertString(dot, inserir, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(DocumentAssembly6502.class.getName()).log(Level.SEVERE, null, ex);
        }
        popup.setVisible(false);
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    public void configurarTextComponent(final JTextComponent jTextPane) {
        jTextPane.setDocument(this);
        jTextPane.addCaretListener(this);
        jTextPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK), "completar");
        jTextPane.getActionMap().put("completar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    int dot = jTextPane.getCaret().getDot();
                    Rectangle caretCoords = jTextPane.modelToView(dot);
                    int index = 0;
                    if (dot > 0) {
                        String text = getText(dot - 1, 1);
                        for (String string : Opcodes.OPCODES_DESCRICAO) {
                            if (string.startsWith(text)) {
                                break;
                            } else {
                                index++;
                            }
                        }
                        if (index == Opcodes.OPCODES_DESCRICAO.length) {
                            for (String string : RegisterNames.REGISTER_DESCRICAO) {
                                if (string.startsWith(text)) {
                                    break;
                                } else {
                                    index++;
                                }
                            }
                        }
                    }
                    ListAutoCompletar.setSelectedIndex(index);
                    ListAutoCompletar.ensureIndexIsVisible(index);
                    popup.show(jTextPane, caretCoords.x, caretCoords.y);
                    ListAutoCompletar.requestFocus();
                } catch (BadLocationException ex) {
                    Logger.getLogger(DocumentAssembly6502.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTextPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "undo");
        jTextPane.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                undo();
            }
        });
        jTextPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "redo");
        jTextPane.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                redo();
            }
        });
    }

    private void criarUndo() {
        undoManager = new UndoManager();
        this.addUndoableEditListener(new UndoableEditListener() {
            boolean start = false;

            @Override
            public void undoableEditHappened(UndoableEditEvent evt) {
                if (!"style change".equals(evt.getEdit().getPresentationName())
                        && !"alteração de estilo".equals(evt.getEdit().getPresentationName())) {
                    if (start) {
                        undoManager.addEdit(evt.getEdit());
                    } else {
                        start = true;
                    }
                }
            }
        });
    }
}
