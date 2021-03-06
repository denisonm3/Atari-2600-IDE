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

/**
 *
 * @author Denison
 */
public class NewProject extends javax.swing.JDialog {

    private ProjectsList projetos;

    /**
     * Creates new form NewProject
     *
     * @param parent
     * @param modal
     */
    public NewProject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setProjetos(ProjectsList projetos) {
        this.projetos = projetos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabelNome = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        javax.swing.JLabel jLabelLocalArq = new javax.swing.JLabel();
        jTextFieldLocalArq = new javax.swing.JTextField();
        jSeparator = new javax.swing.JSeparator();
        jLabelP1Informacao = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButtonProximo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Enter the name of the Project", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 12))); // NOI18N

        jLabelNome.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        jLabelNome.setText("Project name");

        jTextFieldNome.setText("New Project");
        jTextFieldNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNomeKeyReleased(evt);
            }
        });

        jLabelLocalArq.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        jLabelLocalArq.setText("File");

        jTextFieldLocalArq.setEditable(false);
        jTextFieldLocalArq.setText(Recursos.PROJECTS_DIR + "\\New Project");

        jSeparator.setForeground(new java.awt.Color(0, 0, 0));

        jLabelP1Informacao.setForeground(new java.awt.Color(204, 0, 0));

        jCheckBox1.setText("Create Hello Word");

        jButtonProximo.setText("Finish");
        jButtonProximo.setEnabled(false);
        jButtonProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNome)
                            .addComponent(jLabelLocalArq))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldLocalArq, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                            .addComponent(jTextFieldNome)))
                    .addComponent(jLabelP1Informacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addComponent(jButtonProximo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNome)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLocalArq)
                    .addComponent(jTextFieldLocalArq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelP1Informacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonProximo)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNomeKeyReleased
        if (this.jTextFieldNome.getText().equals("")) {
            this.jLabelP1Informacao.setText("Provide a valid name");
            this.jTextFieldLocalArq.setText("");
            this.jButtonProximo.setEnabled(false);
        } else {
            if (projetos.has(jTextFieldNome.getText())) {
                this.jLabelP1Informacao.setText(this.jLabelP1Informacao.getText() + "\nThis project name already exists");
                this.jButtonProximo.setEnabled(false);
            } else {
                this.jLabelP1Informacao.setText("");
                this.jTextFieldLocalArq.setText(Recursos.PROJECTS_DIR.getAbsolutePath() + "\\" + this.jTextFieldNome.getText());
                this.jButtonProximo.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jTextFieldNomeKeyReleased

    private void jButtonProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProximoActionPerformed
        Projeto novoProjeto = new Projeto(jTextFieldNome.getText(),jCheckBox1.isSelected());
        projetos.addElement(novoProjeto);
        jButtonProximo.setEnabled(false);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonProximoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonProximo;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabelP1Informacao;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTextField jTextFieldLocalArq;
    private javax.swing.JTextField jTextFieldNome;
    // End of variables declaration//GEN-END:variables
}
