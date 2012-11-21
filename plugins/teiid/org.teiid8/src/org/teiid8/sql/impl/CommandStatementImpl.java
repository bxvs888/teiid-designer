/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid8.sql.impl;

import org.teiid.designer.query.sql.ILanguageVisitor;
import org.teiid.designer.query.sql.proc.ICommandStatement;
import org.teiid.query.sql.proc.CommandStatement;

/**
 *
 */
public class CommandStatementImpl extends StatementImpl implements ICommandStatement {

    /**
     * @param commandStatement
     */
    public CommandStatementImpl(CommandStatement commandStatement) {
        super(commandStatement);
    }

    @Override
    public CommandStatement getDelegate() {
        return (CommandStatement) delegate;
    }

    @Override
    public void acceptVisitor(ILanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public CommandStatementImpl clone() {
        return new CommandStatementImpl((CommandStatement) getDelegate().clone());
    }
}