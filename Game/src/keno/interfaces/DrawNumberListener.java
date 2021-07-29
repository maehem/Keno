/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno.interfaces;

/**
 *
 * @author mark
 */
public interface DrawNumberListener {
    public boolean numberDrawn(int n);
    public void clear();
    public void gameStarted();
    public void gameOver();
    public void clearPicks();
}
