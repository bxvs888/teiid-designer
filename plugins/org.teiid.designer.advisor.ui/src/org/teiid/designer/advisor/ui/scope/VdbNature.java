/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.advisor.ui.scope;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.teiid.designer.advisor.ui.AdvisorUiConstants;
import org.teiid.designer.core.ModelerCore;


public class VdbNature implements IProjectNature {

    // ===========================================================================================================================
    // Fields
    // ===========================================================================================================================

    private IProject project;

    public static final String NATURE_ID = AdvisorUiConstants.PLUGIN_ID + ".vdbNature"; //$NON-NLS-1$
    public static final String[] NATURES = new String[] {
    	ModelerCore.NATURE_ID, 
    	RelationalModelingNature.NATURE_ID, 
    	XmlModelingNature.NATURE_ID,
    	VdbNature.NATURE_ID};

    // ===========================================================================================================================
    // Methods
    // ===========================================================================================================================

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    @Override
	public void configure() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    @Override
	public void deconfigure() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    @Override
	public IProject getProject() {
        return this.project;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    @Override
	public void setProject( IProject project ) {
        this.project = project;
    }
}

