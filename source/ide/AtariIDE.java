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

import static ide.Recursos.LOGO;
import ide.gui.Atari2600IDE;
import ide.gui.SplashWindow;
import java.awt.Color;
import java.io.PrintStream;

/**
 *
 * @author Denison
 */
public class AtariIDE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SplashWindow window = new SplashWindow(LOGO);
        window.setText("Atari 2600 IDE. By Denison, 2014");
        window.setVisible(true);

        //Carrega recusos utilizados pela IDE
        Recursos res = new Recursos();
        //Carrega IDE
        Atari2600IDE ide = new Atari2600IDE();
        //Transfere output do texto para interface
        JTextAreaOutputStream out = new JTextAreaOutputStream(ide.getNotificacoes(), Color.BLACK);
        System.setOut(new PrintStream(out));
        JTextAreaOutputStream err = new JTextAreaOutputStream(ide.getNotificacoes(), Color.RED);
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        window.dispose();
        //Inicia aplicação
        ide.setVisible(true);
    }

}
