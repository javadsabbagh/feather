package org.fibers.apps.dtree.util;

import javax.swing.*;

public class SwingUtil {
    public static void runSwingAction(Runnable action) {
        SwingUtilities.invokeLater(action);
    }
}
