/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.teiid.core.designer.util.CoreArgCheck;
import org.teiid.designer.ui.UiConstants;
import org.teiid.designer.ui.undo.IUndoManager;


/**
 * The <code>UndoAction</code> class is the action that handles the global undo.
 * 
 * @since 8.0
 */
public class UndoAction extends AbstractUndoRedoAction {

    // ===========================================================================================================================
    // Constructors
    // ===========================================================================================================================

    public UndoAction() {
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
        setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
        setAccelerator(SWT.CTRL | 'Z');
    }

    // ===========================================================================================================================
    // Methods
    // ===========================================================================================================================

    /**
     * @see org.teiid.designer.ui.actions.AbstractUndoRedoAction#performAction(org.teiid.designer.ui.undo.IUndoManager,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * @since 5.5
     */
    @Override
    protected void performAction( IUndoManager undoMgr,
                                  IProgressMonitor monitor ) {
        CoreArgCheck.isTrue((undoMgr != null) && undoMgr.canUndo(), "Undo Manager cannot be null"); //$NON-NLS-1$
        undoMgr.undo(monitor);
    }

    /**
     * @see org.teiid.designer.ui.actions.AbstractUndoRedoAction#updateState(org.teiid.designer.ui.undo.IUndoManager)
     * @since 5.5
     */
    @Override
    protected void updateState( IUndoManager undoMgr ) {
        boolean enable = false;
        String text = null;

        if ((undoMgr != null) && undoMgr.canUndo()) {
            enable = true;
            text = undoMgr.getUndoLabel();
        } else {
            text = UiConstants.Util.getString("org.teiid.designer.ui.actions.UndoAction.text"); //$NON-NLS-1$
        }

        setEnabled(enable);
        setText(text);
        setToolTipText(text);
    }
}
