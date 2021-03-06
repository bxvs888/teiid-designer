/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.mapping.ui.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.diagram.ui.editor.DiagramEditorUtil;
import org.teiid.designer.mapping.ui.UiConstants;
import org.teiid.designer.mapping.ui.UiPlugin;
import org.teiid.designer.metamodels.transformation.SqlTransformationMappingRoot;
import org.teiid.designer.transformation.util.TransformationHelper;
import org.teiid.designer.ui.common.eventsupport.SelectionUtilities;
import org.teiid.designer.ui.editors.ModelEditor;



/** 
 * @since 8.0
 */
public class LockAction extends MappingAction {
    private static final String LOCK_ACTION_DESCRIPTION = UiConstants.Util.getString("LockAction.lockText"); //$NON-NLS-1$
    private static final String UNLOCK_ACTION_DESCRIPTION = UiConstants.Util.getString("LockAction.unlockText"); //$NON-NLS-1$
    private boolean lockMode = true; 
        
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public LockAction() {
        super();
        setImageAndText();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
    public void selectionChanged(IWorkbenchPart thePart, ISelection theSelection) {
        super.selectionChanged(thePart, theSelection);
        lockMode = !isTargetLocked();
        determineEnablement();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.action.IAction#run()
     */
    @Override
    protected void doRun() {
        if( getMappingClassFactory() != null  ) {
            Object o = SelectionUtilities.getSelectedObject(getSelection());
            if( o instanceof EObject ) {
                //start txn
                boolean requiredStart = ModelerCore.startTxn(true, true, getLockDescription(), this);
                boolean succeeded = false;
                try {
                    SqlTransformationMappingRoot target = getRoot();
                    if( target != null ) {
                        target.setOutputReadOnly(lockMode);
                        succeeded = true;
                    }
                } finally {
                    if(requiredStart){
                        if ( succeeded ) {
                            ModelerCore.commitTxn( );
                        } else {
                            ModelerCore.rollbackTxn( );
                        }
                    }
                }
                
            } else {
                // LOG AN ERROR HERE!!
            }
        }
        lockMode = !lockMode;
        determineEnablement();
    }
    
    
    private void determineEnablement() {
        // This is an action that requires two things...
        // 1) Single Selection
        // 2) Selected object can allow mapping class
        boolean enable = false;
        if ( this.getPart() instanceof ModelEditor && SelectionUtilities.isSingleSelection(getSelection())) {
            EObject eObject = SelectionUtilities.getSelectedEObject(getSelection());
            if( eObject != null && isWritable() ) {
                if( this.isMappingClass(eObject) &&  !this.isStagingTable(eObject) && TransformationHelper.isVirtual(eObject)  ) {
                    if( isDetailed() ) {
                        // Need to get the diagram's target
                        EObject target = DiagramEditorUtil.getCurrentVisibleDiagramTarget();
                        // If detailed and selected mapping class is the target, then enable
                        if( target != null && target.equals(eObject) ) {
                            enable = true;
                        }
                    } else {
                        enable = true;
                    }
                    
                }
            }
        }
        setImageAndText();
        setEnabled(enable);
    }
    
    public SqlTransformationMappingRoot getRoot() {
        // Need to get the root for the selected mapping class??
        EObject eObject = SelectionUtilities.getSelectedEObject(getSelection());
        SqlTransformationMappingRoot tRoot = null;
        if( eObject != null && eObject.eResource() != null && TransformationHelper.isVirtual(eObject) ) {
            if( isDetailed() ) {
                tRoot = (SqlTransformationMappingRoot)this.getTransformation();
            } else if( this.isMappingClass(eObject) &&  !this.isStagingTable(eObject) ){
                // get the root for the mapping class
                tRoot = (SqlTransformationMappingRoot)TransformationHelper.getMappingRoot(eObject);
            }
        }
        return tRoot;
    }
    
    public boolean isTargetLocked() {
        SqlTransformationMappingRoot tRoot = getRoot();
        if( tRoot != null )
            return tRoot.isOutputReadOnly();
        return false;
    }
    
    private void setImageAndText() {
        if( lockMode ) {
            setImageDescriptor(UiPlugin.getDefault().getImageDescriptor(UiConstants.Images.LOCK_MAPPING_CLASS));
        } else {
            setImageDescriptor(UiPlugin.getDefault().getImageDescriptor(UiConstants.Images.UNLOCK_MAPPING_CLASS));
        }
        setText(getLockDescription());
    }
    
    private String getLockDescription() {
        if( lockMode )
            return LOCK_ACTION_DESCRIPTION;
        
        return UNLOCK_ACTION_DESCRIPTION;
    }
}
