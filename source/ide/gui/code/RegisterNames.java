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
package ide.gui.code;

/**
 *
 * @author Denison
 */
public class RegisterNames {

    //By http://atariage.com/forums/topic/81139-glossary/
    public final static String[] REGISTER_DESCRICAO = {
          "AUDC0 - audio control 0\n"
        , "AUDC1 - audio control 1\n"
        , "AUDF0 - audio frequency 0\n"
        , "AUDF1 - audio frequency 1\n"
        , "AUDV0 - audio volume 0\n"
        , "AUDV1 - audio volume 1\n"
        , "COLUBK - color (hue) and luminance of the background\n"
        , "COLUP0 - color (or hue) and luminance of player 0 and missile 0\n"
        , "COLUP1 - color (hue) and luminance of player 1 and missile 1\n"
        , "COLUPF - color (hue) and luminance of the playfield and the ball\n"
        , "CTRLPF - control settings for the playfield, ball, and object priorities\n"
        , "CXCLR - collision registers clear\n"
        , "CXM1FB - collision detection between missile 1 and the playfield or ball\n"
        , "CXM1P - collision detection between missile 1 and player 0 or player 1\n"
        , "CXP1FB - collision detection between player 1 and the playfield or ball\n"
        , "CXPPMM - collision detection between the players or the missiles\n"
        , "ENAM1 - enable control for missile 1\n"
        , "GRP0 - the graphics register for player 0\n"
        , "GRP1 - the graphics register for player 1\n"
        , "HMCLR - horizontal movement registers clear\n"
        , "HMM1 - horizontal movement register for missile 1\n"
        , "HMOVE - horizontal movement command\n"
        , "HMP0 - horizontal movement register for player 0\n"
        , "HMP1 - horizontal movement register for player 1\n"
        , "INPT4 - input port 4\n"
        , "INTIM - inspect or interrogate (?) the timer (check the value in the timer)\n"
        , "NUSIZ0 - number and size of player 0 and missile 0\n"
        , "NUSIZ1 - number and size of player 1 and missile 1\n"
        , "PF0 - the graphics register for playfield 0\n"
        , "PF1 - the graphics register for playfield 1\n"
        , "PF2 - the graphics register for playfield 2\n"
        , "RESM1 - reset the horizontal position of missile 1\n"
        , "RESP0 - reset the horizontal position of player 0\n"
        , "RESP1 - reset the horizontal position of player 1\n"
        , "SWCHA - switches for port A\n"
        , "SWCHB - switches for port B\n"
        , "TIM64T - set the timer to 64 times the number of indicated cycles\n"
        , "TIM8T - set the timer to 8 times the number of indicated cycles\n"
        , "VDELP0 - vertical delay for player 0\n"
        , "VDELP1 - vertical delay for player 1\n"
        , "WSYNC - wait for horizontal sync"
    };
    
    public final static String[] REGISTER_NOME = {
        "\\bAUDC0\\b",
        "\\bAUDC1\\b",
        "\\bAUDF0\\b",
        "\\bAUDF1\\b",
        "\\bAUDV0\\b",
        "\\bAUDV1\\b",
        "\\bCOLUBK\\b",
        "\\bCOLUP0\\b",
        "\\bCOLUP1\\b",
        "\\bCOLUPF\\b",
        "\\bCTRLPF\\b",
        "\\bCXCLR\\b",
        "\\bCXM1FB\\b",
        "\\bCXM1P\\b",
        "\\bCXP1FB\\b",
        "\\bCXPPMM\\b",
        "\\bENAM1\\b",
        "\\bGRP0\\b",
        "\\bGRP1\\b",
        "\\bHMCLR\\b",
        "\\bHMM1\\b",
        "\\bHMOVE\\b",
        "\\bHMP0\\b",
        "\\bHMP1\\b",
        "\\bINPT4\\b",
        "\\bINTIM\\b",
        "\\bNUSIZ0\\b",
        "\\bNUSIZ1\\b",
        "\\bPF0\\b",
        "\\bPF1\\b",
        "\\bPF2\\b",
        "\\bRESM1\\b",
        "\\bRESP0\\b",
        "\\bRESP1\\b",
        "\\bSWCHA\\b",
        "\\bSWCHB\\b",
        "\\bTIM64T\\b",
        "\\bTIM8T\\b",
        "\\bVDELP0\\b",
        "\\bVDELP1\\b",
        "\\bWSYNC\\b"
    };
}