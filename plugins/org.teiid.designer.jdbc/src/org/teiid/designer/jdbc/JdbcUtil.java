/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.jdbc;

import java.sql.SQLWarning;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * JdbcUtil
 *
 * @since 8.0
 */
public class JdbcUtil {

    /**
     * Construct an instance of JdbcUtil.
     * 
     */
    private JdbcUtil() {
    }

    public static IStatus createOkIStatus() {
        return new Status(IStatus.OK,JdbcPlugin.PLUGIN_ID,0,"",null); //$NON-NLS-1$
    }

    /**
     * Utility method to create from an SQLWarning an IStatus with {@link IStatus#getSeverity() severity}
     * of {@link IStatus#WARNING}.
     * @param e the warning from which the IStatus is to be created; may not be null
     * @return the new IStatus
     */
    public static IStatus createIStatus( final SQLWarning e ) {
        return new Status(IStatus.WARNING,JdbcPlugin.PLUGIN_ID,0,e.getMessage(),e);
    }

    /**
     * Utility method to create from a Throwable an IStatus with {@link IStatus#getSeverity() severity}
     * of {@link IStatus#ERROR}.
     * @param e the exception from which the IStatus is to be created; may not be null
     * @return the new IStatus
     */
    public static IStatus createIStatus( final Throwable e ) {
        final String msg = e.getMessage() != null ? e.getMessage() : ""; //$NON-NLS-1$
        return new Status(IStatus.ERROR,JdbcPlugin.PLUGIN_ID,0,msg,e);
    }

    /**
     * Utility method to create from a Throwable an IStatus with {@link IStatus#getSeverity() severity}
     * of {@link IStatus#ERROR}.
     * @param e the exception from which the IStatus is to be created
     * @param msg the message for the IStatus; may not be null
     * @return the new IStatus
     */
    public static IStatus createIStatus( final Throwable e, final String msg ) {
        return new Status(IStatus.ERROR,JdbcPlugin.PLUGIN_ID,0,msg,e);
    }

    /**
     * Utility method to create an IStatus from a String and a severity.
     * @param severity the severity; see {@link IStatus}
     * @param msg the message for the IStatus; may not be null
     * @return the new IStatus
     */
    public static IStatus createIStatus( final int severity, final String msg ) {
        return new Status(severity,JdbcPlugin.PLUGIN_ID,0,msg,null);
    }

    /**
     * Utility method to return an IStatus that encapsulates the list of IStatus instances.  If the list
     * is null, this method returns null.  If there is only on IStatus in the list, this method returns
     * that IStatus.  Otherewise, this method creates a new {@link MultiStatus} and adds all of the IStatus
     * instances (if the list has any)
     * @param statuses the list of IStatus instances; may be null or empty
     * @param outerMsg the message to be placed in a wrapping (outer) IStatus if one is needed
     * @return the new IStatus that wraps or represents the supplied IStatus instances
     */
    public static IStatus createIStatus( final List statuses, final String outerMsg ) {
        if ( statuses == null ) {
            return null;
        }
        else if ( statuses.size() == 1 && statuses.get(0) instanceof IStatus ) {
            // Return the one IStatus
            return (IStatus) statuses.get(0);
        } else {
            // Create the IStatus with any problems encountered
            final MultiStatus result = new MultiStatus(JdbcPlugin.PLUGIN_ID,0,outerMsg,null);
            final Iterator iter = statuses.iterator();
            while (iter.hasNext()) {
                final Object obj = iter.next();
                if ( obj instanceof IStatus ) {
                    result.add((IStatus)obj);
                }
            }
            return result;
        }
    }

    public static JdbcSource findJdbcSource(Resource resource) {
        if (resource != null) {
            for (final Iterator it = resource.getContents().iterator(); it.hasNext();) {
                Object o = it.next();
                if (o instanceof JdbcSource) {
                    return (JdbcSource) o;
                }
            }
        }
        return null;
    }
    
}
