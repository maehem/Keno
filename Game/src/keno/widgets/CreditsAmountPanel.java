/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno.widgets;

/**
 *
 * @author mark
 */
public class CreditsAmountPanel extends AmountPanel {

    @Override
    public void gameOver(int picks, int pay) {
        creditAmount(pay, ANIMATE, 500 );
    }


}
