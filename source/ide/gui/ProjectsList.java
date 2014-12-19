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

    private final JLabel itemProjeto;
    private final JLabel itemFile;

    public ProjectsList() {
        File[] projects = Recursos.PROJECTS_DIR.listFiles();
        for (File project : projects) {
            if (project.isDirectory()) {
                addElement(new Projeto(project));
                File[] files = project.listFiles();
                for (File file : files) {
                    if(file.getName().endsWith(".h")) {
                        addElement(new Projeto(file));
                    }
                }
            }
        }
        itemProjeto = new JLabel("texto");
        itemProjeto.setIcon(Recursos.ICON);
        itemProjeto.setOpaque(true);
        itemFile = new JLabel("-");
        itemFile.setBackground(Color.WHITE);
        itemFile.setOpaque(true);
    }

    @Override
    public Projeto get(int index) {
        if (super.get(index) instanceof Projeto) {
            return (Projeto) super.get(index);
        } else {
            return null;
        }
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(((Projeto)value).isFile()) {
            if(isSelected) {
                itemFile.setBackground(Color.LIGHT_GRAY);
            } else if(itemFile.getBackground().equals(Color.LIGHT_GRAY)){
                itemFile.setBackground(Color.WHITE);
            }
            itemFile.setText("         ● "+value.toString());
            return itemFile;
        }
        itemProjeto.setText(value.toString());
        if (isSelected) {
            itemProjeto.setBackground(Color.BLUE);
            itemProjeto.setForeground(Color.WHITE);
        } else {
            itemProjeto.setBackground(Color.WHITE);
            itemProjeto.setForeground(Color.BLACK);
        }
        return itemProjeto;
    }

    /**
     * Verifica se existe um elemento com nome especificado
     *
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
