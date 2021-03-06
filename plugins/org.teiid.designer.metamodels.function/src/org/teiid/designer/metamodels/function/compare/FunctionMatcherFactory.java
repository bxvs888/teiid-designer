/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.function.compare;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.teiid.designer.core.compare.EObjectMatcherFactory;
import org.teiid.designer.metamodels.function.FunctionPackage;



/**
 * FunctionPackage 
 * @since 8.0
 */
public class FunctionMatcherFactory implements
                                   EObjectMatcherFactory {

    private final List standardMatchers;

    /**
     * Construct an instance of XmlMatcherFactory.
     */
    public FunctionMatcherFactory() {
        super();
        this.standardMatchers = new LinkedList();
        this.standardMatchers.add( new FuntionNameToNameMatcher() );
        this.standardMatchers.add( new FunctionParameterNameToNameMatcher() );
        this.standardMatchers.add( new FunctionNameToNameIgnoreCaseMatcher() );
        this.standardMatchers.add( new FunctionParameterNameToNameIgnoreCaseMatcher() );
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchersForRoots()
     */
    @Override
	public List createEObjectMatchersForRoots() {
        // Relational objects can be roots, so return the matchers 
        return this.standardMatchers;
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchers(org.eclipse.emf.ecore.EReference)
     */
    @Override
	public List createEObjectMatchers(final EReference reference) {
        // Make sure the reference is in the function metamodel ...
        final EClass containingClass = reference.getEContainingClass();
        final EPackage metamodel = containingClass.getEPackage();
        if ( !FunctionPackage.eINSTANCE.equals(metamodel) ) {
            // The feature isn't in the function metamodel so return nothing ...
            return Collections.EMPTY_LIST;
        }

        return this.standardMatchers;
    }

}
