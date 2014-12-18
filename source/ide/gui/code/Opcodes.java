/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide.gui.code;

/**
 *
 * @author Denison
 */
public class Opcodes {
    
    public static String[] OPCODES_DESCRICAO = {
        "ADC .... add with carry\n", "AND .... and (with accumulator)\n", "ASL .... arithmetic shift left\n",
        "BCC .... branch on carry clear\n", "BCS .... branch on carry set\n", "BEQ .... branch on equal (zero set)\n",
        "BIT .... bit test\n", "BMI .... branch on minus (negative set)\n", "BNE .... branch on not equal (zero clear)\n",
        "BPL .... branch on plus (negative clear)\n", "BRK .... interrupt\n", "BVC .... branch on overflow clear\n", "BVS .... branch on overflow set\n",
        "CLC .... clear carry\n", "CLD .... clear decimal\n", "CLI .... clear interrupt disable\n", "CLV .... clear overflow\n",
        "CMP .... compare (with accumulator)\n", "CPX .... compare with X\n", "CPY .... compare with Y\n",
        "DEC .... decrement\n", "DEX .... decrement X\n", "DEY .... decrement Y\n",
        "EOR .... exclusive or (with accumulator)\n", "INC .... increment\n", "INX .... increment X\n",
        "INY .... increment Y\n", "JMP .... jump\n", "JSR .... jump subroutine\n", "LDA .... load accumulator\n",
        "LDX .... load X\n", "LDY .... load Y\n", "LSR .... logical shift right\n", "NOP .... no operation\n",
        "ORA .... or with accumulator\n", "PHA .... push accumulator\n", "PHP .... push processor status (SR)\n",
        "PLA .... pull accumulator\n", "PLP .... pull processor status (SR)\n", "ROL .... rotate left\n",
        "ROR .... rotate right\n", "RTI .... return from interrupt\n", "RTS .... return from subroutine\n",
        "SBC .... subtract with carry\n", "SEC .... set carry\n", "SED .... set decimal\n", "SEI .... set interrupt disable\n",
        "STA .... store accumulator\n", "STX .... store X\n", "STY .... store Y\n", "TAX .... transfer accumulator to X\n",
        "TAY .... transfer accumulator to Y\n", "TSX .... transfer stack pointer to X\n", "TXA .... transfer X to accumulator\n",
        "TXS .... transfer X to stack pointer\n", "TYA .... transfer Y to accumulator"
    };
    
    public static String[] OPCODES_NOME = {
        "\\bADC\\b", "\\bAND\\b", "\\bASL\\b",
        "\\bBCC\\b", "\\bBCS\\b", "\\bBEQ\\b",
        "\\bBIT\\b", "\\bBMI\\b", "\\bBNE\\b",
        "\\bBPL\\b", "\\bBRK\\b", "\\bBVC\\b", "\\bBVS\\b",
        "\\bCLC\\b", "\\bCLD\\b", "\\bCLI\\b", "\\bCLV\\b",
        "\\bCMP\\b", "\\bCPX\\b", "\\bCPY\\b",
        "\\bDEC\\b", "\\bDEX\\b", "\\bDEY\\b",
        "\\bEOR\\b", "\\bINC\\b", "\\bINX\\b",
        "\\bINY\\b", "\\bJMP\\b", "\\bJSR\\b", "\\bLDA\\b",
        "\\bLDX\\b", "\\bLDY\\b", "\\bLSR\\b", "\\bNOP\\b",
        "\\bORA\\b", "\\bPHA\\b", "\\bPHP\\b",
        "\\bPLA\\b", "\\bPLP\\b", "\\bROL\\b",
        "\\bROR\\b", "\\bRTI\\b", "\\bRTS\\b",
        "\\bSBC\\b", "\\bSEC\\b", "\\bSED\\b", "\\bSEI\\b",
        "\\bSTA\\b", "\\bSTX\\b", "\\bSTY\\b", "\\bTAX\\b",
        "\\bTAY\\b", "\\bTSX\\b", "\\bTXA\\b",
        "\\bTXS\\b", "\\bTYA\\b"
    };
}
