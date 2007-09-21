/*
 * DefaultSplitPaneLayout.java
 *
 * Created on November 21, 2006, 10:41 AM
 *
 */

package org.jrichclient.richdock.multisplitpane;

/**
 * A simplified SplitPaneLayout for common split pane needs. A common multi splitpane
 * need is:
 *
 * +-----------+-----------+
 * |           |           |
 * |           +-----------+
 * |           |           |
 * +-----------+-----------+
 *
 * @author rbair
 */
public class DefaultSplitPaneModel extends MultiSplitLayout.Split {
    public static final String LEFT = "left";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    
    /** Creates a new instance of DefaultSplitPaneLayout */
    public DefaultSplitPaneModel() {
        MultiSplitLayout.Split col = new MultiSplitLayout.Split();
        col.setRowLayout(false);
        setChildren(new MultiSplitLayout.Leaf(LEFT), new MultiSplitLayout.Divider(), col);
        col.setChildren(new MultiSplitLayout.Leaf(TOP), new MultiSplitLayout.Divider(), new MultiSplitLayout.Leaf(BOTTOM));
    }
    
}

