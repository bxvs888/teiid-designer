/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.diagram.ui.util.directedit;

import org.teiid.designer.diagram.ui.part.EditableEditPart;

/**
 * DirectEditPart
 *
 * @since 8.0
 */
public interface DirectEditPart extends EditableEditPart {
    String getText();
    
    void setText(String newText);
    
    String getEditString();
    
    void performDirectEdit();
    
    DirectEditPartManager getEditManager();
}
