package ide.gui;

import ide.Projeto;
import ide.Recursos;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Denison
 */
public class ProjectsList extends DefaultListModel implements ListCellRenderer {

    private final JLabel item;

    public ProjectsList() {
        File[] projects = Recursos.PROJECTS_DIR.listFiles();
        for (File project : projects) {
            if (project.isDirectory()) {
                addElement(new Projeto(project));
            }
        }
        item = new JLabel("texto");
        item.setIcon(Recursos.ICON);
        item.setOpaque(true);
    }

    @Override
    public Projeto get(int index) {
        return (Projeto) super.get(index);
    }

    @Override
    public Projeto getElementAt(int index) {
        return (Projeto) super.getElementAt(index);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        item.setText(value.toString());
        if (isSelected) {
            item.setBackground(Color.BLUE);
            item.setForeground(Color.WHITE);
        } else {
            item.setBackground(Color.WHITE);
            item.setForeground(Color.BLACK);
        }
        return item;
    }

    /**
     * Verifica se existe um elemento com nome especificado
     * @param nome nome do projeto
     * @return true se existir, false se não encontrar
     */
    public boolean has(String nome) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

}
