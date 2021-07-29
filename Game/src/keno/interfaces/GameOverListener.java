/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno.interfaces;

/**
 *
 * @author mark
 */
public interface GameOverListener {
    public void gameOver( int picks, int pay );
    public void isPlayable( boolean playable );
}
